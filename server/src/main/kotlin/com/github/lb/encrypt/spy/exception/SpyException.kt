package com.github.lb.encrypt.spy.exception

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午10:32 xingliangbo Exp $
 */
 class SpyException constructor(var httpCode :Int, var msg:String) :Exception(msg) {

    var  serviceCode :Int = httpCode

}