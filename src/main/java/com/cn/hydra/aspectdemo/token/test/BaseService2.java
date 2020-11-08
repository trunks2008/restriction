package com.cn.hydra.aspectdemo.token.test;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:36
 **/
public class BaseService2 {
    
    public static ThreadLocal<String> TOKEN= ThreadLocal.withInitial(() -> null);

}
