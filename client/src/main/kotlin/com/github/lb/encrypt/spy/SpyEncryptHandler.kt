package com.github.lb.encrypt.spy

import com.github.lb.encrypt.spy.config.SpyConfig
import com.github.lb.encrypt.spy.domain.ReqKeyPair
import com.github.lb.encrypt.spy.service.KeySwapService
import com.github.lb.encrypt.spy.util.AesEncrypt
import com.google.common.base.Splitter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午11:13 xingliangbo Exp $
 */
class SpyEncryptHandler constructor(val keySwapService: KeySwapService, val spyConfig: SpyConfig) {

    var log = LoggerFactory.getLogger(SpyEncryptHandler::class.java)

    //此处需要加缓存
    fun encrypt(origintext: String): String {
        try {
            val keyPair = keySwapService.getKeyPairFromServer(serviceId = spyConfig.serviceId, cipher = spyConfig.cipher).execute().body()
            if(null == keyPair){
                return origintext;
            }
            val originCipherText = Base64.getEncoder().encodeToString(AesEncrypt.encrypt(origintext, keyPair.publicKey).toByteArray())
            val cipherText= "${String(Base64.getEncoder().encode(spyConfig.serviceId.toByteArray()))}.${keyPair.privateKey}.${originCipherText}"
            return cipherText
        } catch (e: Exception) {
            log.error("加密失败 ", e)
            return origintext
        }
    }

    //此处需要加缓存
    fun decrypt(cipherText: String): String {
        val splitterList = Splitter.on(".").split(cipherText).toList()
        if (splitterList?.size ?: 0 != 4) {
            log.error("解密失败，参数非法 {} ", cipherText)
            return cipherText
        }
        try {
            val privateKey = "${splitterList[1]}.${splitterList[2]}"
            val publicKeyPair = keySwapService.swapPublicKeyPairFromServer(ReqKeyPair(spyConfig.serviceId, spyConfig.cipher, privateKey)).execute().body()
            if(null == publicKeyPair){
                log.error("解密失败，交换公钥失败 {} ",  cipherText)
                return cipherText
            }
            val cipherTextBase64Decode = String(Base64.getDecoder().decode(splitterList[3]))
            val originCipherText = AesEncrypt.decrypt(cipherTextBase64Decode, publicKeyPair.publicKey)
            return originCipherText
        } catch (e: Exception) {
            log.error("解密失败，服务异常 {} ", e, cipherText)
            return cipherText
        }
    }


}