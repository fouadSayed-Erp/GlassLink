
package com.glasslink.data.p2p
import android.content.Context
import com.glasslink.data.crypto.CryptoManager
import com.glasslink.domain.model.*
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.KeyPair
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NearbyP2pManager @Inject constructor(
    private val context: Context,
    private val crypto: CryptoManager
) {
    private val client = Nearby.getConnectionsClient(context)
    private val strategy = Strategy.P2P_CLUSTER
    private val _peers = MutableStateFlow<List<Peer>>(emptyList())
    val peers: StateFlow<List<Peer>> = _peers.asStateFlow()
    private val _messages = MutableSharedFlow<DecryptedMessage>(extraBufferCapacity = 100)
    val messages: SharedFlow<DecryptedMessage> = _messages.asSharedFlow()
    private var localKeyPair: KeyPair = crypto.generateECDHKeyPair()
    private val sessionKeys = ConcurrentHashMap<String, SecretKey>()

    private val connectionLifecycle = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            client.acceptConnection(endpointId, payloadCallback)
            client.sendPayload(endpointId, Payload.fromBytes(crypto.encodePublicKey(localKeyPair.public)))
        }
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                _peers.update { list ->
                    if (list.any { it.endpointId == endpointId }) list.map { if (it.endpointId==endpointId) it.copy(isConnected=true) else it }
                    else list + Peer(endpointId, endpointId, true, "WIFI_HOTSPOT_HIGH")
                }
            }
        }
        override fun onDisconnected(endpointId: String) {
            sessionKeys.remove(endpointId)
            _peers.update { it.map { p -> if(p.endpointId==endpointId) p.copy(isConnected=false) else p } }
        }
    }

    private val discoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            _peers.update { if (it.any { p->p.endpointId==endpointId }) it else it + Peer(endpointId, info.endpointName) }
            client.requestConnection("GlassLinkUser", endpointId, connectionLifecycle)
        }
        override fun onEndpointLost(endpointId: String) { _peers.update { it.filterNot { p->p.endpointId==endpointId } } }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            if (payload.type == Payload.Type.BYTES) {
                val bytes = payload.asBytes() ?: return
                if (!sessionKeys.containsKey(endpointId)) {
                    try {
                        val remotePub = crypto.decodePublicKey(bytes)
                        val shared = crypto.deriveSharedSecret(remotePub, localKeyPair.private)
                        sessionKeys[endpointId] = crypto.deriveAesKey(shared)
                    } catch (_: Exception) {}
                } else {
                    try {
                        val decrypted = crypto.decryptAESGCM(bytes, sessionKeys[endpointId]!!)
                        val msg = Json.decodeFromString<Message>(String(decrypted))
                        _messages.tryEmit(DecryptedMessage(endpointId, msg))
                    } catch (e: Exception) { e.printStackTrace() }
                }
            }
        }
        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    fun start(userName: String) {
        client.startAdvertising(userName, context.packageName, connectionLifecycle, AdvertisingOptions.Builder().setStrategy(strategy).build())
        client.startDiscovery(context.packageName, discoveryCallback, DiscoveryOptions.Builder().setStrategy(strategy).build())
    }
    fun stop() { client.stopAllEndpoints(); client.stopAdvertising(); client.stopDiscovery() }
    suspend fun sendEncrypted(endpointId: String, message: Message) {
        val key = sessionKeys[endpointId] ?: return
        val plain = Json.encodeToString(message).toByteArray()
        val enc = crypto.encryptAESGCM(plain, key)
        client.sendPayload(endpointId, Payload.fromBytes(enc))
    }
}
