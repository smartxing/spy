package com.github.lb.encrypt.spy.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午11:47 xingliangbo Exp $
 */
@Configuration
@ConfigurationProperties("spyconfig.spy")
open class SpyConfig {
    var serviceId: String = ""
    var cipher: String = ""

}