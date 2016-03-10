package com.zzhoujay.dailygank.util

import java.security.MessageDigest

/**
 * Created by zzhoujay on 2015/7/31 0031.
 * 加密相关工具类
 */
object HashKit {

    private val random = java.security.SecureRandom()

    fun md5(srcStr: String): String {
        return hash("MD5", srcStr)
    }

    fun sha1(srcStr: String): String {
        return hash("SHA-1", srcStr)
    }

    fun sha256(srcStr: String): String {
        return hash("SHA-256", srcStr)
    }

    fun sha384(srcStr: String): String {
        return hash("SHA-384", srcStr)
    }

    fun sha512(srcStr: String): String {
        return hash("SHA-512", srcStr)
    }

    fun hash(algorithm: String, srcStr: String): String {
        try {
            val result = StringBuilder()
            val md = MessageDigest.getInstance(algorithm)
            val bytes = md.digest(srcStr.toByteArray(charset("utf-8")))
            for (b in bytes) {
                val hex = Integer.toHexString(b.toInt() and 0xFF)
                if (hex.length == 1)
                    result.append("0")
                result.append(hex)
            }
            return result.toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun toHex(bytes: ByteArray): String {
        val result = StringBuilder()
        for (b in bytes) {
            val hex = Integer.toHexString(b.toInt() and 0xFF)
            if (hex.length == 1)
                result.append("0")
            result.append(hex)
        }
        return result.toString()
    }

    /**
     * md5 128bit 16bytes
     * sha1 160bit 20bytes
     * sha256 256bit 32bytes
     * sha384 384bit 48bites
     * sha512 512bit 64bites
     */
    fun generateSalt(numberOfBytes: Int): String {
        val salt = ByteArray(numberOfBytes)
        random.nextBytes(salt)
        return toHex(salt)
    }
}
