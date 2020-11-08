package com.cn.hydra.aspectdemo.rule.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-11-06 15:45
 **/
public class WindowCloseEvent extends ApplicationEvent {
    @Getter
    private String windowKey;

    public WindowCloseEvent(Object source, String windowKey) {
        super(source);
        this.windowKey = windowKey;
    }

}
