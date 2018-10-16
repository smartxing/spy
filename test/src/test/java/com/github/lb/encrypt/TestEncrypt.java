package com.github.lb.encrypt;


import com.github.lb.encrypt.spy.SpyEncryptHandler;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author xingliangbo
 * @version $Id: v 0.1 2018/10/9 下午9:10 xingliangbo Exp $
 */
@SpringBootTest(classes = App.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class TestEncrypt extends AbstractTestNGSpringContextTests{

    @Autowired
    private SpyEncryptHandler spyEncryptHandler;

    @Test
    public void testEncrypt() throws Exception {

        String testEncrypt = spyEncryptHandler.encrypt("123456");
        System.out.println(testEncrypt);
        String testDecrypt = spyEncryptHandler.decrypt(testEncrypt);
        System.out.println(testDecrypt);
        Assert.assertEquals(testDecrypt,"123456");
    }



}
