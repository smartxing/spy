package com.github.lb.encrypt.spy

import com.github.lb.encrypt.spy.config.SpyConfig
import com.github.lb.encrypt.spy.service.KeySwapService
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 下午2:42 xingliangbo Exp $
 */
@Configuration
open class AutoConfig{

    @Bean
    open fun getSpyEncryptHandler(spyConfig: SpyConfig) : SpyEncryptHandler{
        val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080/spy/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val keySwapService = retrofit.create(KeySwapService::class.java)
        return SpyEncryptHandler(keySwapService,spyConfig);
    }
}