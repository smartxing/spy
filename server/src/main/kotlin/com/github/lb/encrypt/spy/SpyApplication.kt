package com.github.lb.encrypt.spy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/8 下午8:25 xingliangbo Exp $
 */
@SpringBootApplication
open class SpyApplication {

}

fun main(args: Array<String>) {
    SpringApplication.run(SpyApplication::class.java, *args)
}

