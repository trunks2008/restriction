package com.cn.hydra.aspectdemo.token.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:19
 **/
//@Aspect
//@Component
public class TokenAspect2 {

    @Pointcut("@annotation(com.cn.hydra.aspectdemo.token.annotation.NeedToken)")
    public void tokenPointCut() {
    }

    @Around("tokenPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        try {

            ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");

            Class<?> baseClazz = point.getTarget().getClass().getSuperclass();
            Field tokenField = baseClazz.getDeclaredField("TOKEN");
            ThreadLocal<String> local = (ThreadLocal<String>) tokenField.get(point.getTarget());
            local.set(token);

            tokenField.setAccessible(true);
            tokenField.set(point.getTarget(),local);

            Object object = point.proceed();
            return object;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
