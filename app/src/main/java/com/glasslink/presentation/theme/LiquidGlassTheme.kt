
package com.glasslink.presentation.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val DarkScheme = darkColorScheme(
    primary = Color(0xFF7C4DFF), secondary = Color(0xFF18FFFF), tertiary = Color(0xFFFF4081),
    background = Color(0xFF0A0A0F), surface = Color(0xFF15151F), onSurface = Color.White
)
@Composable
fun LiquidGlassTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkScheme, typography = Typography(), content = content)
}
