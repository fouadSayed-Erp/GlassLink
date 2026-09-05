
package com.glasslink.presentation.call
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glasslink.presentation.components.GlassButton
import com.glasslink.presentation.components.GlassCard
@Composable
fun CallScreen(isVideo: Boolean, onEnd: ()->Unit) {
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        GlassCard(Modifier.align(Alignment.Center)) { Text("Voice/Video - E2EE - Local WiFi", color=Color.White) }
        Row(Modifier.align(Alignment.BottomCenter).padding(32.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GlassButton(Icons.Default.MicOff, onEnd)
            GlassButton(Icons.Default.CallEnd, onEnd)
        }
    }
}
