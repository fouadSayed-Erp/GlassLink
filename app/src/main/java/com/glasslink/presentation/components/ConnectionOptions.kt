package com.glasslink.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ConnectionMode { WIFI, BLUETOOTH, AUTO }

data class ConnectionOption(
    val mode: ConnectionMode,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun ConnectionOptionsSheet(
    selected: ConnectionMode,
    onSelect: (ConnectionMode) -> Unit
) {
    val options = listOf(
        ConnectionOption(ConnectionMode.WIFI, "Wi-Fi Direct", "سريع • مدى 100 متر", Icons.Default.Wifi, Color(0xFF7C4DFF)),
        ConnectionOption(ConnectionMode.BLUETOOTH, "Bluetooth", "موفر للبطارية • مدى 10 متر", Icons.Default.Bluetooth, Color(0xFF29B6F6)),
        ConnectionOption(ConnectionMode.AUTO, "Auto", "يختار الأفضل تلقائي", Icons.Default.Wifi, Color(0xFF66BB6A))
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("طريقة الاتصال", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
        
        options.forEach { opt ->
            val isSelected = selected == opt.mode
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (isSelected) Brush.linearGradient(listOf(opt.color.copy(0.25f), opt.color.copy(0.08f)))
                        else Brush.linearGradient(listOf(Color.White.copy(0.12f), Color.White.copy(0.04f)))
                    )
                    .border(
                        1.dp,
                        if (isSelected) opt.color.copy(0.5f) else Color.White.copy(0.15f),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable { onSelect(opt.mode) }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        Modifier.size(44.dp).clip(CircleShape)
                            .background(opt.color.copy(0.2f))
                            .border(1.dp, opt.color.copy(0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(opt.icon, null, tint = opt.color, modifier = Modifier.size(22.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(opt.title, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                        Text(opt.subtitle, color = Color.White.copy(0.6f), fontSize = 12.sp)
                    }
                    if (isSelected) {
                        Icon(Icons.Default.Check, null, tint = opt.color, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}
