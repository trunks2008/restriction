package com.cn.hydra.aspectdemo.rule.test;

import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindow;

import java.util.concurrent.TimeUnit;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-07 16:49
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
        MethodAccessWindow methodAccessWindow =new MethodAccessWindow(20,5,"4");
        for (int i = 0; i < 26; i++) {
            System.out.println(i+"  "+ methodAccessWindow.canReceive());
            TimeUnit.SECONDS.sleep(1);
        }
//        methodAccessWindow.destroy();
    }
}
