
package com.glasslink.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.glasslink.presentation.chat.ChatListScreen
import com.glasslink.presentation.scanner.NearbyScannerScreen
import com.glasslink.presentation.vault.MediaVaultScreen
sealed class Screen(val route: String){ object Scanner: Screen("scanner"); object Chats: Screen("chats"); object Vault: Screen("vault") }
@Composable
fun AppNavGraph(peers: List<com.glasslink.domain.model.Peer>) {
    val nav = rememberNavController()
    NavHost(nav, startDestination=Screen.Scanner.route) {
        composable(Screen.Scanner.route){ NearbyScannerScreen(peers,{}) }
        composable(Screen.Chats.route){ ChatListScreen(peers,{}) }
        composable(Screen.Vault.route){ MediaVaultScreen() }
    }
}
