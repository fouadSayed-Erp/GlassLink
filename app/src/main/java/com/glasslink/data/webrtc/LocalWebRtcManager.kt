
package com.glasslink.data.webrtc
import android.content.Context
import org.webrtc.*
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class LocalWebRtcManager @Inject constructor(private val context: Context) {
    private var factory: PeerConnectionFactory? = null
    var peerConnection: PeerConnection? = null
    private val eglBase = EglBase.create()
    init { initFactory() }
    private fun initFactory() {
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(context).createInitializationOptions())
        factory = PeerConnectionFactory.builder()
            .setVideoDecoderFactory(DefaultVideoDecoderFactory(eglBase.eglBaseContext))
            .setVideoEncoderFactory(DefaultVideoEncoderFactory(eglBase.eglBaseContext, true, true))
            .setAudioDeviceModule( // JavaAudioDeviceModule.builder(context).createAudioDeviceModule())
            .createPeerConnectionFactory()
    }
    fun createConnection(observer: PeerConnection.Observer): PeerConnection {
        val config = PeerConnection.RTCConfiguration(emptyList()).apply {
            bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE
            sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN
        }
        peerConnection = factory?.createPeerConnection(config, observer)
        return peerConnection!!
    }
    fun createVideoCapturer(): CameraVideoCapturer = Camera2Enumerator(context).run {
        deviceNames.firstOrNull { isFrontFacing(it) }?.let { createCapturer(it, null) } ?: createCapturer(deviceNames[0], null)
    }
    fun getEglContext() = eglBase.eglBaseContext
    fun close() { peerConnection?.close(); peerConnection=null }
}
