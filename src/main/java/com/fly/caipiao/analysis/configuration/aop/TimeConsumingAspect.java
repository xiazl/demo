package com.fly.caipiao.analysis.configuration.aop;

import com.fly.caipiao.analysis.configuration.anoutation.TimeConsuming;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author baidu
 * @date 2018/6/20 下午2:59
 * @description ${TODO}
 **/

@Aspect
@Component
@Order(2)
public class TimeConsumingAspect {

    private static final Logger logger = LoggerFactory.getLogger(TimeConsumingAspect.class);

    @Around("@annotation(tc)")
    public Object execute(ProceedingJoinPoint joinPoint, TimeConsuming tc) throws Throwable {

        long start = System.currentTimeMillis();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String className = methodSignature.getMethod().getDeclaringClass().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        String methodDesc = className + "." + methodName;

        try {

            return joinPoint.proceed();

        } finally {

            long end = System.currentTimeMillis();

            logger.info("=============>>>功能描述 = {}, 方法 {} 消耗时间 {} ms",tc.value(), methodDesc, end - start);
        }
    }
}