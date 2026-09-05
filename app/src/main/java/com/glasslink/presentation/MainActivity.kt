
package com.glasslink.presentation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.glasslink.presentation.navigation.AppNavGraph
import com.glasslink.presentation.theme.LiquidGlassTheme
import com.glasslink.presentation.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiquidGlassTheme {
                val vm: ChatViewModel = hiltViewModel()
                val peers by vm.peers.collectAsState()
                AppNavGraph(peers)
            }
        }
    }
}
