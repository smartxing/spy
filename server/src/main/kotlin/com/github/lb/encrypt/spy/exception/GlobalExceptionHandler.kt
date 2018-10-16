package com.github.lb.encrypt.spy.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.lang.Exception
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 上午10:29 xingliangbo Exp $
 */
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(SpyException::class)
    @ResponseBody
    fun handleSpyException(e: SpyException, response: HttpServletResponse): SpyApiExceptionResp {
        val spyApiExceptionResp =  SpyApiExceptionResp()
        response.status = e.httpCode
        spyApiExceptionResp.code = e.serviceCode
        spyApiExceptionResp.message = e.msg
        return spyApiExceptionResp

    }


    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e: Exception, response: HttpServletResponse): SpyApiExceptionResp {
        val spyApiExceptionResp =  SpyApiExceptionResp()
        response.status = 500
        spyApiExceptionResp.code = 500
        spyApiExceptionResp.message = "未知异常"
        return spyApiExceptionResp

    }



}