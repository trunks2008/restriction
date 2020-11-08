package com.cn.hydra.aspectdemo.token.annotation;

import java.lang.annotation.*;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:18
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedToken {
    String token() default "";
}
