package com.cn.hydra.aspectdemo.rule.aspect;

import com.cn.hydra.aspectdemo.rule.annotation.NeedPassLimit;
import com.cn.hydra.aspectdemo.rule.util.MethodAccessWindow;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-11-05 15:39
 **/
@Component
@Aspect
public class MethodPassAspect {

    //缓存各个用户的 Window
    @Getter
    private ConcurrentHashMap<String, MethodAccessWindow> passerMap =new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.cn.hydra.aspectdemo.rule.annotation.NeedPassLimit)")
    public void freshPointCut() {
    }

    @Around("freshPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(NeedPassLimit.class)) {
            Object[] args = point.getArgs();
            String[] parameterNames = signature.getParameterNames();
            List<String> paramNameList = Arrays.asList(parameterNames);

            String passerKey=null;
            if (paramNameList.contains("userId")) {
                passerKey=point.getTarget().getClass().getName()
                        +"#"+method.getName()+"#"+args[paramNameList.indexOf("userId")];
            }

            NeedPassLimit annotation = method.getAnnotation(NeedPassLimit.class);
            int timeWindow = annotation.timeWindow();
            int frequency = annotation.frequency();
            String fallBackMethodName = annotation.fallBackMethod();

            MethodAccessWindow methodAccessWindow;
            if (passerMap.keySet().contains(passerKey)) {
                methodAccessWindow = passerMap.get(passerKey);
            }else {
                //第一次，放过请求
                methodAccessWindow= new MethodAccessWindow(timeWindow,frequency,passerKey);
                passerMap.put(passerKey,methodAccessWindow);

                Object object = point.proceed();
                return object;
            }

            if (methodAccessWindow.canReceive()){
                Object fallbackObject = invokeFallbackMethod(method, point.getTarget(), fallBackMethodName, args);
                return fallbackObject;
            }else{
                Object object = point.proceed();
                methodAccessWindow.reset();
                return object;
            }
        }
        return null;
    }

    private Object invokeFallbackMethod(Method method, Object bean, String fallbackMethodName, Object[] arguments) throws Exception {
        Class beanClass = bean.getClass();
        Method fallbackMethod = beanClass.getMethod(fallbackMethodName, method.getParameterTypes());
        Object fallbackObject = fallbackMethod.invoke(bean, arguments);
        return fallbackObject;
    }

}
