package com.cn.hydra.aspectdemo.rule.annotation;

import java.lang.annotation.*;

/**
 * 刷新物流放行規則
 * 單一用戶60秒內強制刷新5次，則強制刷新
 * @Author: Hydra
 * @Date: 2020/10/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedPassLimit {

    int timeWindow() default 100;

    int frequency() default 10;

    String fallBackMethod() default "";

}
