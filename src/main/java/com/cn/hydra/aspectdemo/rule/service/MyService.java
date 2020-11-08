package com.cn.hydra.aspectdemo.rule.service;

import com.cn.hydra.aspectdemo.rule.annotation.NeedPassLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-07 16:45
 **/
@Service
@Slf4j
public class MyService {

    @NeedPassLimit(timeWindow = 20, frequency = 5, fallBackMethod = "fallback")
    public String getInfo(Long userId){
        log.info("放行："+userId.toString());
        return "success";
    }

    public String fallback(Long userId){
        log.info("拦截："+userId.toString());
        return "restriction";
    }
}
