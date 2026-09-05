package com.glasslink.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glasslink.domain.model.Message
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold

@Composable
fun ChatScreen(messages: List<Message>, onSend: (String) -> Unit, onVoice: () -> Unit) {
    var text by remember { mutableStateOf("") }
    GlassScaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(messages) { m ->
                    GlassCard(modifier = Modifier.fillMaxWidth(0.8f).padding(4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(m.text, color = Color.White)
                            Text("AES-GCM 256", style = MaterialTheme.typography.labelSmall, color = Color(0xFF18FFFF))
                        }
                    }
                }
            }
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = text, onValueChange = { text = it }, modifier = Modifier.weight(1f), placeholder = { Text("Message...") })
                Button(onClick = { if(text.isNotBlank()){ onSend(text); text="" } }) { Text("Send") }
            }
        }
    }
}
