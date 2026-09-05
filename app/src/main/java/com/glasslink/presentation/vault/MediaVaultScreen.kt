
package com.glasslink.presentation.vault
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glasslink.presentation.components.GlassCard
import com.glasslink.presentation.components.GlassScaffold
@Composable
fun MediaVaultScreen() {
    GlassScaffold(topBar={ GlassCard(Modifier.fillMaxWidth().padding(16.dp)){ Text("Media Vault - Encrypted at Rest", color=Color.White)} }) {
        GlassCard(Modifier.padding(16.dp).fillMaxWidth()) { Text("All received files are AES-GCM encrypted and stored in app private storage.", color=Color.Gray) }
    }
}
