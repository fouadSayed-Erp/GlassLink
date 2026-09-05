
package com.glasslink.presentation.components
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val blur = if (Build.VERSION.SDK_INT >= 31) Modifier.graphicsLayer {
        renderEffect = RenderEffect.createBlurEffect(30f,30f,Shader.TileMode.CLAMP)
    } else Modifier
    Box(modifier = modifier.clip(RoundedCornerShape(24.dp))
        .background(Brush.linearGradient(listOf(Color.White.copy(alpha=0.15f), Color.White.copy(alpha=0.05f))))
        .border(1.dp, Brush.linearGradient(listOf(Color(0xFF7C4DFF).copy(0.5f), Color(0xFF18FFFF).copy(0.3f))), RoundedCornerShape(24.dp))
        .then(blur).padding(20.dp)) { content() }
}
