package com.glasslink.presentation.nearby

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold

@Composable
fun NearbyScreen(peers: List<String> = emptyList()) {
    val infinite = rememberInfiniteTransition(label = "radar")
    val pulse by infinite.animateFloat(0.9f, 1.15f, infiniteRepeatable(tween(1800), RepeatMode.Reverse), label = "")

    GlassScaffold {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            GlassCard(Modifier.fillMaxWidth()) {
                Column {
                    Text("Nearby Peers", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("${peers.size} peers nearby • Scanning...", color = Color(0xFFB388FF), fontSize = 13.sp)
                }
            }
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                repeat(3) { i ->
                    Box(Modifier.size((220 + i * 90).dp).scale(pulse - i*0.07f).clip(CircleShape).border(1.dp, Color(0xFF7C4DFF).copy(0.15f - i*0.03f), CircleShape))
                }
                Box(Modifier.size(180.dp).clip(CircleShape).background(Brush.radialGradient(listOf(Color(0xFF7C4DFF).copy(0.5f), Color(0xFF311B92).copy(0.2f)))).border(1.dp, Color.White.copy(0.25f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Wifi, null, tint = Color.White, modifier = Modifier.size(56.dp))
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(if(peers.isEmpty()) listOf("Alex • 12m away", "Mira • 25m away") else peers) { p ->
                    GlassCard(Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(42.dp).clip(CircleShape).background(Color(0xFF7C4DFF)), contentAlignment = Alignment.Center) { Text(p.first().toString(), color = Color.White) }
                            Spacer(Modifier.width(12.dp))
                            Column { Text(p, color = Color.White); Text("• Online • Encrypted", color = Color.White.copy(0.5f), fontSize = 11.sp) }
                        }
                    }
                }
            }
        }
    }
}
