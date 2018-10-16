package com.github.lb.encrypt.controller;

import com.github.lb.encrypt.spy.SpyEncryptHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 下午3:07 xingliangbo Exp $
 */
@RestController
public class TestController {

    @Autowired
    private SpyEncryptHandler spyEncryptHandler;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        String test = spyEncryptHandler.encrypt("123456");
        System.out.println(test);
        System.out.println(spyEncryptHandler.decrypt(test));
    }

}
