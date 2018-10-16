package com.github.lb.encrypt.spy.service

import com.github.lb.encrypt.spy.domain.KeyPair
import com.github.lb.encrypt.spy.domain.ReqKeyPair
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午11:17 xingliangbo Exp $
 */
interface KeySwapService {

    @GET("v1/keys/{serviceId}/{cipher}")
    fun getKeyPairFromServer(@Path("serviceId") serviceId: String, @Path("cipher") cipher: String): Call<KeyPair>

    @POST("v1/keys/publickey")
    fun swapPublicKeyPairFromServer(@Body reqKeyPair: ReqKeyPair):  Call<KeyPair>

}