package com.github.lb.encrypt;

import com.github.lb.encrypt.spy.SpyEncryptHandler;
import com.github.lb.encrypt.spy.config.SpyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {

    @Autowired
    private SpyEncryptHandler spyEncryptHandler;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
