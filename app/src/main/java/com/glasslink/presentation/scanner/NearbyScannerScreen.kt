package com.glasslink.presentation.scanner
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.glasslink.domain.model.Peer
import com.glasslink.presentation.components.*

@Composable
fun NearbyScannerScreen(
    peers: List<Peer> = emptyList(),
    onPeerClick: (Peer) -> Unit = {}
) {
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
                        Column { 
                            Text("GlassLink v1.8 CHAT FIX", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("${peers.size} peers • ${selectedMode.name}", color = radarColor, fontSize = 12.sp) 
                        }
                        Box(Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(0.15f)).border(1.dp, Color.White.copy(0.3f), CircleShape).clickable { showOptions = true }, contentAlignment = Alignment.Center) { Icon(Icons.Default.Settings, null, tint = Color.White, modifier=Modifier.size(28.dp)) }
                    }
                }
                
                Box(Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) {
                    repeat(3) { i -> Box(Modifier.size((180 + i * 60).dp).scale(pulse - i*0.07f).clip(CircleShape).border(1.dp, radarColor.copy(0.15f - i*0.04f), CircleShape)) }
                    Box(Modifier.size(120.dp).clip(CircleShape).background(Brush.radialGradient(listOf(radarColor.copy(0.5f), radarColor.copy(0.15f)))).border(1.5.dp, Color.White.copy(0.25f), CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Wifi, null, tint = Color.White, modifier = Modifier.size(40.dp)) }
                }

                GlassCard(Modifier.fillMaxWidth().weight(1f)) {
                    Column {
                        Text("القريبين منك", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
                        if (peers.isEmpty()) {
                            Box(Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center) {
                                Text("مفيش حد قريب - بيعمل Scan...", color = Color.White.copy(0.5f), fontSize = 13.sp)
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(peers) { peer ->
                                    Row(
                                        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White.copy(0.08f)).border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp)).clickable { onPeerClick(peer) }.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(Modifier.size(40.dp).clip(CircleShape).background(radarColor.copy(0.3f)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White) }
                                        Spacer(Modifier.width(12.dp))
                                        Column(Modifier.weight(1f)) {
                                            Text(peer.toString(), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                                            Text("دوس لفتح الدردشة", color = Color.White.copy(0.5f), fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
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
