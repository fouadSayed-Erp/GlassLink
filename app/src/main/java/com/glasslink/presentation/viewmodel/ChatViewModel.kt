
package com.glasslink.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glasslink.data.p2p.NearbyP2pManager
import com.glasslink.domain.model.Message
import com.glasslink.domain.model.MessageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
@HiltViewModel
class ChatViewModel @Inject constructor(private val p2p: NearbyP2pManager) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages
    val peers = p2p.peers
    init {
        viewModelScope.launch { p2p.messages.collect { dec -> _messages.update { it + dec.message.copy(isMe=false) } } }
        p2p.start("User-"+UUID.randomUUID().toString().take(4))
    }
    fun send(peerId: String, text: String) {
        val msg = Message(UUID.randomUUID().toString(), "me", peerId, text, System.currentTimeMillis(), MessageType.TEXT, true)
        _messages.update { it + msg }
        viewModelScope.launch { p2p.sendEncrypted(peerId, msg) }
    }
}
