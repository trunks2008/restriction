package com.cn.hydra.aspectdemo.rule.test;

import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindow;
import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindowSimple;

import java.util.concurrent.TimeUnit;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-07 16:49
 **/
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        MethodAccessWindowSimple methodAccessWindow =new MethodAccessWindowSimple(20,5);
        for (int i = 0; i < 30; i++) {
            System.out.println(i+"  "+ methodAccessWindow.canReceive());
            TimeUnit.SECONDS.sleep(1);
        }
        methodAccessWindow.destroy();
    }
}
