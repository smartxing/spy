# spy （加解密服务设计）
## 设计文档  

##### 对于一个互联网公司来说，数据的加密低于公司来说越来越重要，被黑客攻击，拖库导数用户数据的泄露，所以需要设计一个可靠的加解密服务防止数据泄露

#### 加密方式
#####  1 MD5/SHA  比如用户app登陆密码  
#####  2 AES/DES  比如铭感性不是很高的数据可以使用
#####  3 混合的加密方式（本次要实现的）  铭感数据加密


#### 实现核心思想
##### 1 秘钥服务端动态生成 防止暴力破解
##### 2 秘钥不落库，通过算法计算 防止泄露



##### 原理分析
```ftl>
    加密
    1 申请服务id和秘钥 ，每个服务是唯一的
    2 获取秘钥对，通过算法产生动态的秘钥对 公钥和私钥是唯一对应的，通过私钥能计算出公钥（秘钥生成算法和salt不同时泄露就无法被破解，并且salt可动态升级）
    3 通过公钥 对原文加密产生密文拼接私钥和服务id 产生最终密文
    4 加密结束
    
    解密
    1 拆分密文 解析出私钥
    2 将私钥通过服务端去交换出公钥
    3 对密文用公钥进行解密，解出明文
    4 解密结束 
    
    //核心的秘钥生成，秘钥交换 逻辑如下
    /**
     * 生成秘钥 
     * 公钥：uuid
     * 私钥 aes（公钥，（md5(serviceid cipher) +slat） 也可以用其他的实现 aes性能高一点 
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
     * 通过私钥交换公钥
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


``` 

##### 示列 参考test即可
```ftl>
1 加解密前 预先申请一串服务标识  理论上在A服务加密只会在A服务做解密
spyconfig.spy.serviceId=testserviceid
spyconfig.spy.cipher=98c9aece6d244478b66c70a8109e577b

2 注入bean即可
   @Autowired
    private SpyEncryptHandler spyEncryptHandler;

    @Test
    public void testEncrypt() throws Exception {

        String testEncrypt = spyEncryptHandler.encrypt("123456");
        String testDecrypt = spyEncryptHandler.decrypt(testEncrypt);
        Assert.assertEquals(testDecrypt,"123456");
    }
3 产生的加密串
    对123456加密的结果
        dGVzdHNlcnZpY2VpZA==.MTAwMDAwMDE=.MmpMS0VpdFc4VjJNQlczRk9WS2dRN3JVRnVhRjZRYVByMlZpSEVmLy9HWT0=.Z3N3bEFEM2lhQ3JKSCtkTlkrOEQrZz09
    
    格式如下
        dGVzdHNlcnZpY2VpZA==（serviceId标识 标识是哪一个serviceId产生的）
        MzcwNw==（加密版本号）
        WEFiMHBtRDFPQUxSNDVJV1BwaHJqN3JVRnVhRjZRYVByMlZpSEVmLy9HWT0=（私钥）
        QmNuV1VSNitvVGw4ZTB1Z0F1TXdGdz09（密文）
    
``` 


##### 此工程只是一个示列，只是简单实现上诉流程 如需真正使用还需要考虑更多的安全问题，认证授权
 





  
 
 



















