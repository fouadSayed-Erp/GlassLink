
package com.glasslink.data.p2p
enum class Transport { BLUETOOTH_LOW, WIFI_HOTSPOT_HIGH, AUTO }
class ConnectionStrategy {
    fun selectTransport(payloadSize: Long, isRealtime: Boolean): Transport {
        return when {
            isRealtime -> Transport.WIFI_HOTSPOT_HIGH
            payloadSize > 50_000 -> Transport.WIFI_HOTSPOT_HIGH
            else -> Transport.BLUETOOTH_LOW
        }
    }
}
