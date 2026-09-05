package com.glasslink.presentation.scanner
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glasslink.presentation.components.*

@Composable 
fun NearbyScannerScreen(
    peers: List<Any> = emptyList(),
    onPeerClick: (Any) -> Unit = {},
    onConnect: (Any) -> Unit = {}
) { NearbyContent(peers) }

@Composable fun NearbyContent(peers: List<Any>) {
    var selectedMode by remember { mutableStateOf(ConnectionMode.AUTO) }
    var showOptions by remember { mutableStateOf(false) }
    val infinite = rememberInfiniteTransition(label="radar")
    val pulse by infinite.animateFloat(0.9f, 1.15f, infiniteRepeatable(tween(1800), RepeatMode.Reverse), label="")
    val radarColor = when(selectedMode) { ConnectionMode.WIFI -> Color(0xFF7C4DFF); ConnectionMode.BLUETOOTH -> Color(0xFF29B6F6); else -> Color(0xFFAB47BC) }
    GlassScaffold { padding ->
        Box(Modifier.fillMaxSize().padding(padding).background(Brush.verticalGradient(listOf(Color(0xFF08080F), Color(0xFF130E2A))))) {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                GlassCard(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column { Text("GlassLink v1.5 FIXED", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("${peers.size} peers • ${selectedMode.name} • دوس ⚙️", color = radarColor, fontSize = 12.sp) }
                        Box(Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(0.15f)).border(1.dp, Color.White.copy(0.3f), CircleShape).clickable { showOptions = true }, contentAlignment = Alignment.Center) { Icon(Icons.Default.Settings, null, tint = Color.White, modifier=Modifier.size(28.dp)) }
                    }
                }
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    repeat(3) { i -> Box(Modifier.size((220 + i * 85).dp).scale(pulse - i*0.07f).clip(CircleShape).border(1.dp, radarColor.copy(0.15f - i*0.04f), CircleShape)) }
                    Box(Modifier.size(180.dp).clip(CircleShape).background(Brush.radialGradient(listOf(radarColor.copy(0.5f), radarColor.copy(0.15f)))).border(1.5.dp, Color.White.copy(0.25f), CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Wifi, null, tint = Color.White, modifier = Modifier.size(56.dp)) }
                }
            }
            if(showOptions) {
                Box(Modifier.fillMaxSize().background(Color.Black.copy(0.6f)).clickable { showOptions = false }, contentAlignment = Alignment.BottomCenter) {
                    GlassCard(Modifier.fillMaxWidth().padding(16.dp)) { ConnectionOptionsSheet(selected = selectedMode) { mode -> selectedMode = mode; showOptions = false } }
                }
            }
        }
    }
}
