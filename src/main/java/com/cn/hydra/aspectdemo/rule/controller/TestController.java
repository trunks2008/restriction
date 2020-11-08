package com.cn.hydra.aspectdemo.rule.controller;

import com.cn.hydra.aspectdemo.rule.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-07 16:45
 **/
@RestController
public class TestController {

    @Autowired
    MyService myService;

    @GetMapping("query")
    public String query(Long userId){
        String info = myService.getInfo(userId);
        return info;
    }

}
