package com.glasslink.data.webrtc

import android.content.Context
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.PeerConnectionFactory

class LocalWebRtcManager(private val context: Context) {
    private var factory: PeerConnectionFactory? = null
    
    fun initFactory() {
        val initOptions = PeerConnectionFactory.InitializationOptions.builder(context)
            .createInitializationOptions()
        PeerConnectionFactory.initialize(initOptions)
        val eglBase = EglBase.create()
        factory = PeerConnectionFactory.builder()
            .setVideoEncoderFactory(DefaultVideoEncoderFactory(eglBase.eglBaseContext, true, true))
            .setVideoDecoderFactory(DefaultVideoDecoderFactory(eglBase.eglBaseContext))
            .createPeerConnectionFactory()
    }
    
    fun getFactory(): PeerConnectionFactory? = factory
}
