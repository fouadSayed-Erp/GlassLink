
package com.glasslink.data.p2p
import android.content.Context
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.Payload
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class FileTransferService @Inject constructor(private val p2p: NearbyP2pManager, private val context: Context) {
    fun sendFile(endpointId: String, file: File) {
        val payload = Payload.fromFile(file)
        Nearby.getConnectionsClient(context).sendPayload(endpointId, payload)
    }
}
