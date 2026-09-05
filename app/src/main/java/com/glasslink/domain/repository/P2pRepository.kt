
package com.glasslink.domain.repository
import com.glasslink.domain.model.*
import kotlinx.coroutines.flow.Flow
interface P2pRepository {
    fun getPeers(): Flow<List<Peer>>
    fun getMessages(): Flow<DecryptedMessage>
    suspend fun sendMessage(peerId: String, text: String)
    fun start(userName: String)
    fun stop()
}
