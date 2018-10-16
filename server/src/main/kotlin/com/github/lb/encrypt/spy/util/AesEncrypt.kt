package com.github.lb.encrypt.spy.util

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午9:41 xingliangbo Exp $
 */

object AesEncrypt {
    //加密
    fun encrypt(input: String, encryptKey: String): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过秘钥工厂生产秘钥
        val keySpec: SecretKeySpec = SecretKeySpec(encryptKey.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        //加密、解密
        val encrypt = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encrypt)
    }

    //解密
    fun decrypt(input: String, encryptKey: String): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过秘钥工厂生产秘钥
        val keySpec: SecretKeySpec = SecretKeySpec(encryptKey.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        //加密、解密
        val encrypt = cipher.doFinal(Base64.getDecoder().decode(input))
        return String(encrypt)
    }
}

