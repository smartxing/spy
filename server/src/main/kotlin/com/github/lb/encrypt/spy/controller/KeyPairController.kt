package com.github.lb.encrypt.spy.controller

import com.github.lb.encrypt.spy.dao.ServerKeyDao
import com.github.lb.encrypt.spy.domain.KeyPair
import com.github.lb.encrypt.spy.domain.ReqKeyPair
import com.github.lb.encrypt.spy.exception.SpyException
import com.github.lb.encrypt.spy.util.AesEncrypt
import com.github.lb.encrypt.spy.util.MD5Utils
import com.google.common.base.Splitter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import sun.security.provider.MD5
import java.util.*

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午9:24 xingliangbo Exp $
 */
@RestController
@RequestMapping(value = "/spy/api/v1", produces = ["application/json"])
class KeyPairController @Autowired constructor(
        private val serverKeyDao: ServerKeyDao) {


    /**
     * 生成秘钥
     */
    @RequestMapping(value = "/keys/{serviceId}/{cipher}", method = arrayOf(RequestMethod.GET))
    fun getKeyPair(@PathVariable("serviceId") serviceId: String, @PathVariable("cipher") cipher: String): KeyPair {
        val serverKey = serverKeyDao.getServerKeyByLastVersionFromDb()
        val md5CipherKey = MD5Utils.getMD5(serviceId + cipher)
        if (md5CipherKey?.length ?: 0 < 6) {
            throw SpyException(400, "参数异常");
        }
        val aesCipherKey = md5CipherKey.substring(0, 6) + serverKey.serverKey
        val publicKey = UUID.randomUUID().toString().replace("-", "").substring(0,16)
        val privateKey =  Base64.getEncoder().encodeToString(serverKey.version.toByteArray()) +"."+ Base64.getEncoder().encodeToString(AesEncrypt.encrypt(publicKey, aesCipherKey).toByteArray())
        return KeyPair(publicKey = publicKey, privateKey = privateKey)
    }


    /**
     * 通过密文秘钥获取明文秘钥
     */
    @RequestMapping(value = "/keys/publickey", method = arrayOf(RequestMethod.POST))
    fun getPublicKey(@RequestBody reqKeyPair: ReqKeyPair): KeyPair {

        val vkeys = Splitter.on(".").split(reqKeyPair.privatekey).toList()
        if (vkeys?.size ?: 0 != 2) {
            throw SpyException(400, "参数异常");
        }
        val version = String(Base64.getDecoder().decode(vkeys[0]))
        val originPrivateKey = String(Base64.getDecoder().decode(vkeys[1]))

        val serverKey = serverKeyDao.getServerKeyByVersionFromDb(version);
        val md5CipherKey = MD5Utils.getMD5(reqKeyPair.serviceId + reqKeyPair.cipher)
        if (md5CipherKey?.length ?: 0 < 6) {
            throw SpyException(400, "参数异常");
        }
        val aesCipherKey = md5CipherKey.substring(0, 6) + serverKey.serverKey
        val publicKey = AesEncrypt.decrypt(originPrivateKey, aesCipherKey)

        return KeyPair(publicKey = publicKey, privateKey = reqKeyPair.privatekey)

    }



    @RequestMapping(value = "/keys/cipher/{serviceId}", method = arrayOf(RequestMethod.GET))
    fun getPublicKey(@PathVariable("serviceId") serviceId: String): String {
       return serverKeyDao.getcipherByServiceId(serviceId)
    }
}