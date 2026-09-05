package com.glasslink.presentation.nearby

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
import com.glasslink.presentation.components.ConnectionMode
import com.glasslink.presentation.components.ConnectionOptionsSheet
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold

@Composable
fun NearbyScreen(
    peers: List<String> = emptyList(),
    onModeChanged: (ConnectionMode) -> Unit = {}
) {
    var selectedMode by remember { mutableStateOf(ConnectionMode.AUTO) }
    var showOptions by remember { mutableStateOf(false) }
    val infinite = rememberInfiniteTransition(label = "radar")
    val pulse by infinite.animateFloat(0.9f, 1.15f, infiniteRepeatable(tween(1800), RepeatMode.Reverse), label = "")

    val radarColor = when(selectedMode) {
        ConnectionMode.WIFI -> Color(0xFF7C4DFF)
        ConnectionMode.BLUETOOTH -> Color(0xFF29B6F6)
        ConnectionMode.AUTO -> Color(0xFFAB47BC)
    }

    GlassScaffold {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                // Top Bar
                GlassCard(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Nearby Peers", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text(
                                when(selectedMode) {
                                    ConnectionMode.WIFI -> "Wi-Fi • ${peers.size} peers"
                                    ConnectionMode.BLUETOOTH -> "Bluetooth • ${peers.size} peers"
                                    ConnectionMode.AUTO -> "${peers.size} peers • Auto"
                                },
                                color = radarColor.copy(0.9f), fontSize = 12.sp
                            )
                        }
                        Box(
                            Modifier.size(38.dp).clip(CircleShape)
                                .background(Color.White.copy(0.12f))
                                .border(1.dp, Color.White.copy(0.2f), CircleShape)
                                .clickable { showOptions = !showOptions },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Settings, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }

                // Radar
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    repeat(3) { i ->
                        Box(Modifier.size((220 + i * 85).dp).scale(pulse - i*0.07f).clip(CircleShape).border(1.dp, radarColor.copy(0.15f - i*0.04f), CircleShape))
                    }
                    Box(
                        Modifier.size(180.dp).clip(CircleShape)
                            .background(Brush.radialGradient(listOf(radarColor.copy(0.5f), radarColor.copy(0.15f))))
                            .border(1.5.dp, Color.White.copy(0.25f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Wifi, null, tint = Color.White, modifier = Modifier.size(56.dp))
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.height(220.dp)) {
                    items(if(peers.isEmpty()) listOf("Alex • 12m away", "Mira • 25m away") else peers) { p ->
                        GlassCard(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(42.dp).clip(CircleShape).background(radarColor.copy(0.25f)), contentAlignment = Alignment.Center) {
                                    Text(p.first().toString(), color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(p, color = Color.White, fontSize = 14.sp)
                                    Text("مشفر • ${if(selectedMode==ConnectionMode.BLUETOOTH) "Bluetooth" else "Wi-Fi"}", color = Color.White.copy(0.5f), fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Sheet للاختيارات
            if (showOptions) {
                Box(
                    Modifier.fillMaxSize().background(Color.Black.copy(0.4f)).clickable { showOptions = false },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                            .clickable(enabled = false) {}
                    ) {
                        ConnectionOptionsSheet(selected = selectedMode) { mode ->
                            selectedMode = mode
                            onModeChanged(mode)
                            showOptions = false
                        }
                    }
                }
            }
        }
    }
}
