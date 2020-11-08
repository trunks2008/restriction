package com.cn.hydra.aspectdemo.rule.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-11-06 15:56
 **/
@Component
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(ApplicationEvent applicationEvent){
        applicationEventPublisher.publishEvent(applicationEvent);
    }
}
