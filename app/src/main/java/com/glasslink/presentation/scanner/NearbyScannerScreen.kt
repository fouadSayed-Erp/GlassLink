
package com.glasslink.presentation.scanner
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glasslink.domain.model.Peer
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold
@Composable
fun NearbyScannerScreen(peers: List<Peer>, onConnect: (Peer)->Unit) {
    val infinite = rememberInfiniteTransition(label="pulse")
    val scale by infinite.animateFloat(1f,1.3f, infiniteRepeatable(tween(1500), RepeatMode.Reverse), label="s")
    GlassScaffold(topBar = { GlassCard(modifier=Modifier.fillMaxWidth().padding(16.dp)) { Text("Nearby Peers - ${peers.size}", color=Color.White) } }) {
        Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
            Box(Modifier.size(120.dp).scale(scale).background(Color(0xFF7C4DFF).copy(0.2f), androidx.compose.foundation.shape.CircleShape))
            Icon(Icons.Default.Wifi, null, tint=Color.White, modifier=Modifier.size(48.dp))
        }
        LazyColumn(Modifier.padding(16.dp)) {
            items(peers) { peer ->
                GlassCard(Modifier.fillMaxWidth().padding(vertical=6.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column { Text(peer.displayName, color=Color.White); Text(peer.transport, style=MaterialTheme.typography.labelSmall, color=Color.Gray) }
                        Button(onClick={onConnect(peer)}) { Text(if(peer.isConnected) "Chat" else "Connect") }
                    }
                }
            }
        }
    }
}
