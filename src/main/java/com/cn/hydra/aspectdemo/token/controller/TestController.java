package com.cn.hydra.aspectdemo.token.controller;

import com.cn.hydra.aspectdemo.token.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:30
 **/
//@RestController
//@RequestMapping("api")
public class TestController {

    @Autowired
    MyService myService;

    @GetMapping("test")
    public String test(){
        String result = myService.test();
        return result;
    }

    @GetMapping("test2")
    public boolean test2(String name){
        boolean result = myService.checkToken(name);
        return result;
    }

    @GetMapping("test3")
    public String test3(){
        String result = myService.test(null);
        return result;
    }

}
