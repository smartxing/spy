package com.github.lb.encrypt.spy.dao

import com.github.lb.encrypt.spy.domain.ServerKey
import org.springframework.stereotype.Component
import java.util.*

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午9:53 xingliangbo Exp $
 */
@Component
class ServerKeyDao {

    /*
        获取最新的version
        此处应有缓存 应该从db中查询
     */
    fun getServerKeyByLastVersionFromDb(): ServerKey {
        return ServerKey("0123456789","10000001")
    }


    /*
        根据版本获取version
        此处应有缓存 应该从db中查询
    */
    fun getServerKeyByVersionFromDb(version:String): ServerKey {
        return ServerKey("0123456789","10000001")
    }

    fun getcipherByServiceId(serviceId:String): String {
        return UUID.randomUUID().toString()
    }
}