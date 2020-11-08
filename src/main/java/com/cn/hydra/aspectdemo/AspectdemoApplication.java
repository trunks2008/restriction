package com.cn.hydra.aspectdemo;

import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AspectdemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AspectdemoApplication.class, args);
        MethodAccessWindow.setEventPubisher(applicationContext);
    }
}
