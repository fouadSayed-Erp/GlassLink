package com.glasslink.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassScaffold(
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF08080F), Color(0xFF130E2A), Color(0xFF0F0A1F))
                )
            )
    ) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.radialGradient(
                    listOf(Color(0xFF7C4DFF).copy(0.18f), Color.Transparent),
                    radius = 900f
                )
            )
        )
        Column(Modifier.fillMaxSize()) {
            topBar()
            Box(Modifier.fillMaxSize()) {
                content(PaddingValues(0.dp))
            }
        }
    }
}

// Overload عشان الشاشات الجديدة اللي بتستخدم GlassScaffold { ... } من غير Padding
@Composable
fun GlassScaffold(
    content: @Composable () -> Unit
) {
    GlassScaffold(topBar = {}) { _ -> content() }
}
