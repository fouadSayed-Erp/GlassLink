
package com.glasslink.presentation.chat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glasslink.domain.model.Peer
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold
@Composable
fun ChatListScreen(peers: List<Peer>, onPeerClick: (Peer)->Unit) {
    GlassScaffold(topBar = { GlassCard(Modifier.fillMaxWidth().padding(16.dp)) { Text("Chats - E2EE Secured", color=Color.White) } }) {
        LazyColumn(Modifier.padding(16.dp)) {
            items(peers.filter{it.isConnected}) { p -> GlassCard(Modifier.fillMaxWidth().padding(6.dp)) { Text(p.displayName, color=Color.White) } }
        }
    }
}
