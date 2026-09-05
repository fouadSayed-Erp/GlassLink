
package com.glasslink.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
@Composable
fun GlassButton(icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(64.dp).clip(CircleShape)
        .background(Brush.radialGradient(listOf(Color(0xFF7C4DFF), Color(0xFF18FFFF))))
        .clickable { onClick() }, contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = null, tint = Color.White)
    }
}
