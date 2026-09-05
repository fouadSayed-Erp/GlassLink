package com.glasslink.presentation.components

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
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color.White.copy(0.18f), Color.White.copy(0.06f))
                )
            )
            .border(
                1.dp,
                Brush.linearGradient(listOf(Color.White.copy(0.35f), Color.White.copy(0.08f))),
                RoundedCornerShape(22.dp)
            )
    ) {
        Box(Modifier.padding(16.dp)) { content() }
    }
}
