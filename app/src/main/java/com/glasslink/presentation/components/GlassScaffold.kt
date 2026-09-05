
package com.glasslink.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
@Composable
fun GlassScaffold(topBar: @Composable ()->Unit = {}, content: @Composable ()->Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF0A0A0F), Color(0xFF1A1A2E), Color(0xFF0A0A0F)))).padding(WindowInsets.systemBars.asPaddingValues())) {
        Column { topBar(); content() }
    }
}
