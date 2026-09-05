
package com.glasslink.data.repository
import com.glasslink.data.p2p.NearbyP2pManager
import com.glasslink.domain.model.*
import com.glasslink.domain.repository.P2pRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
class P2pRepositoryImpl @Inject constructor(private val manager: NearbyP2pManager) : P2pRepository {
    override fun getPeers(): Flow<List<Peer>> = manager.peers
    override fun getMessages(): Flow<DecryptedMessage> = manager.messages
    override suspend fun sendMessage(peerId: String, text: String) {
        val msg = Message(UUID.randomUUID().toString(), "me", peerId, text, System.currentTimeMillis(), MessageType.TEXT, true)
        manager.sendEncrypted(peerId, msg)
    }
    override fun start(userName: String) = manager.start(userName)
    override fun stop() = manager.stop()
}
