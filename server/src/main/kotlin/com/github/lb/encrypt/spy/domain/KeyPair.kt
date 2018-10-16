package com.github.lb.encrypt.spy.domain

import java.security.PublicKey

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午9:22 xingliangbo Exp $
 */

data class KeyPair constructor(
        var publicKey: String,
        var privateKey: String) {

}