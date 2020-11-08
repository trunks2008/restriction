package com.cn.hydra.aspectdemo.token.controller;

import com.cn.hydra.aspectdemo.token.service.MyService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:30
 **/
@RestController
@RequestMapping("api2")
public class TestController2 {

    @Autowired
    MyService2 myService2;

    @GetMapping("test2")
    public boolean test2(String name){
        boolean returnMsg = myService2.testToken(name);
        return returnMsg;
    }

}
