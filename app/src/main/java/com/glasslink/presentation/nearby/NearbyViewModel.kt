package com.glasslink.presentation.nearby

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.glasslink.data.nearby.NearbyConnectionManager
import com.glasslink.presentation.components.ConnectionMode

class NearbyViewModel(app: Application) : AndroidViewModel(app) {
    private val manager = NearbyConnectionManager(app)
    val peers = mutableStateListOf<String>()
    var selectedMode: ConnectionMode = ConnectionMode.AUTO
        private set

    init {
        manager.startDiscovery()
        manager.startAdvertising()
        // Demo peers لحد ما جهاز تاني يظهر
        peers.addAll(listOf("Alex • 12m away", "Mira • 25m away"))
    }

    fun onModeSelected(mode: ConnectionMode) {
        selectedMode = mode
        manager.setMode(mode)
        peers.clear()
        peers.addAll(
            when(mode) {
                ConnectionMode.WIFI -> listOf("Searching Wi-Fi peers...")
                ConnectionMode.BLUETOOTH -> listOf("Searching BT peers...")
                ConnectionMode.AUTO -> listOf("Alex • 12m away", "Mira • 25m away")
            }
        )
    }
}
