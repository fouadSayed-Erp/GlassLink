package com.glasslink.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
@Composable
fun GlassScaffold(topBar: @Composable () -> Unit = {}, content: @Composable (PaddingValues) -> Unit) {
    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF08080F), Color(0xFF130E2A))))) {
        Column(Modifier.fillMaxSize()) { topBar(); Box(Modifier.fillMaxSize()) { content(PaddingValues()) } }
    }
}
