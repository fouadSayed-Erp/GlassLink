
package com.glasslink.data.crypto
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Singleton
@Singleton
class CryptoManager {
    private val ecSpec = ECGenParameterSpec("secp256r1")
    fun generateECDHKeyPair(): KeyPair {
        val kpg = KeyPairGenerator.getInstance("EC")
        kpg.initialize(ecSpec, SecureRandom())
        return kpg.generateKeyPair()
    }
    fun deriveSharedSecret(remotePublic: PublicKey, localPrivate: PrivateKey): ByteArray {
        val ka = KeyAgreement.getInstance("ECDH")
        ka.init(localPrivate)
        ka.doPhase(remotePublic, true)
        return ka.generateSecret()
    }
    fun deriveAesKey(sharedSecret: ByteArray): SecretKey {
        val md = MessageDigest.getInstance("SHA-256")
        md.update("GlassLink-E2EE-v1".toByteArray())
        md.update(sharedSecret)
        return SecretKeySpec(md.digest(), "AES")
    }
    fun encryptAESGCM(plain: ByteArray, key: SecretKey): ByteArray {
        val nonce = ByteArray(12).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, nonce))
        return nonce + cipher.doFinal(plain)
    }
    fun decryptAESGCM(encrypted: ByteArray, key: SecretKey): ByteArray {
        val nonce = encrypted.copyOfRange(0,12)
        val ct = encrypted.copyOfRange(12, encrypted.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, nonce))
        return cipher.doFinal(ct)
    }
    fun encodePublicKey(key: PublicKey): ByteArray = key.encoded
    fun decodePublicKey(bytes: ByteArray): PublicKey {
        return KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(bytes))
    }
}
