package com.cn.hydra.aspectdemo.token.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:19
 **/
//@Aspect
//@Component
public class TokenAspect0 {

    @Pointcut("@annotation(com.cn.hydra.aspectdemo.token.annotation.NeedToken)")
    public void tokenPointCut() {
    }

    @Around("tokenPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");

        Object[] args = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] paramName = methodSignature.getParameterNames();
        List<String> paramNameList = Arrays.asList(paramName);
        if (paramNameList.contains("token")){
            int pos = paramNameList.indexOf("token");
            args[pos]=token;
        }

        Object object = point.proceed(args);
        return object;
    }
}
