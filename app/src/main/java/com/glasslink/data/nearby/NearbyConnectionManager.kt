package com.glasslink.data.nearby

import android.content.Context
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.glasslink.presentation.components.ConnectionMode

class NearbyConnectionManager(private val context: Context) {
    private val client = Nearby.getConnectionsClient(context)
    var currentMode: ConnectionMode = ConnectionMode.AUTO
        private set

    private val strategy: Strategy
        get() = when(currentMode) {
            ConnectionMode.WIFI -> Strategy.P2P_STAR // سريع - WiFi
            ConnectionMode.BLUETOOTH -> Strategy.P2P_CLUSTER // موفر - Bluetooth
            ConnectionMode.AUTO -> Strategy.P2P_CLUSTER
        }

    fun setMode(mode: ConnectionMode) {
        currentMode = mode
        stopAll()
        startDiscovery()
    }

    fun startDiscovery() {
        val options = DiscoveryOptions.Builder().setStrategy(strategy).build()
        client.startDiscovery(
            "GlassLink", endpointDiscoveryCallback, options
        )
    }

    fun startAdvertising() {
        val options = AdvertisingOptions.Builder().setStrategy(strategy).build()
        client.startAdvertising(
            android.os.Build.MODEL, "GlassLink",
            connectionLifecycleCallback, options
        )
    }

    fun stopAll() {
        client.stopDiscovery()
        client.stopAdvertising()
        client.stopAllEndpoints()
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(id: String, info: DiscoveredEndpointInfo) {
            // هنا تطلب الاتصال
            client.requestConnection("GlassLink-${android.os.Build.MODEL}", id, connectionLifecycleCallback)
        }
        override fun onEndpointLost(id: String) {}
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(id: String, info: ConnectionInfo) {
            client.acceptConnection(id, payloadCallback)
        }
        override fun onConnectionResult(id: String, result: ConnectionResolution) {}
        override fun onDisconnected(id: String) {}
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(id: String, payload: Payload) {}
        override fun onPayloadTransferUpdate(id: String, update: PayloadTransferUpdate) {}
    }
}
