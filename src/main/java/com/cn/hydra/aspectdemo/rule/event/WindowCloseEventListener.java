package com.cn.hydra.aspectdemo.rule.event;

import com.cn.hydra.aspectdemo.rule.aspect.MethodPassAspect;
import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-11-06 15:47
 **/
@Component
@Slf4j
public class WindowCloseEventListener implements ApplicationListener<WindowCloseEvent> {
    @Autowired
    MethodPassAspect methodPassAspect;

    @Override
    public void onApplicationEvent(WindowCloseEvent windowCloseEvent) {
        log.info("close:"+windowCloseEvent.getWindowKey());

        ConcurrentHashMap<String, MethodAccessWindow> passerMap = methodPassAspect.getPasserMap();
        System.out.println(passerMap.toString());

        passerMap.remove(windowCloseEvent.getWindowKey());
        System.out.println(passerMap.toString());
    }
}
