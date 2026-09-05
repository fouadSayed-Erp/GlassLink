
package com.glasslink.di
import android.content.Context
import com.glasslink.data.crypto.CryptoManager
import com.glasslink.data.crypto.KeyStoreManager
import com.glasslink.data.p2p.FileTransferService
import com.glasslink.data.p2p.NearbyP2pManager
import com.glasslink.data.webrtc.LocalWebRtcManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module @InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun provideCrypto(): CryptoManager = CryptoManager()
    @Provides @Singleton fun provideKeyStore(): KeyStoreManager = KeyStoreManager()
    @Provides @Singleton fun provideNearby(@ApplicationContext ctx: Context, crypto: CryptoManager) = NearbyP2pManager(ctx, crypto)
    @Provides @Singleton fun provideFileTransfer(nearby: NearbyP2pManager, @ApplicationContext ctx: Context) = FileTransferService(nearby, ctx)
    @Provides @Singleton fun provideWebRtc(@ApplicationContext ctx: Context) = LocalWebRtcManager(ctx)
}
