
package com.glasslink.domain.model
import kotlinx.serialization.Serializable
@Serializable data class Peer(val endpointId: String, val displayName: String, val isConnected: Boolean = false, val transport: String = "UNKNOWN")
@Serializable data class Message(val id: String, val fromId: String, val toId: String, val text: String, val timestamp: Long, val type: MessageType = MessageType.TEXT, val isMe: Boolean = false)
enum class MessageType { TEXT, VOICE, FILE, SYSTEM }
@Serializable data class FilePayload(val name: String, val size: Long, val mime: String, val payloadId: Long)
data class DecryptedMessage(val peerId: String, val message: Message)
data class CallState(val isInCall: Boolean = false, val isVideo: Boolean = false, val peer: Peer? = null)
