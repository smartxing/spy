package com.github.lb.encrypt.spy.domain

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 下午4:01 xingliangbo Exp $
 */

class ReqKeyPair {

    constructor() {

    }

    constructor(serviceId: String, cipher: String, privatekey: String) {
        this.serviceId = serviceId
        this.cipher = cipher
        this.privatekey = privatekey
    }

    var serviceId: String = ""
    var cipher: String = ""
    var privatekey: String = ""
}