package com.mclabs.learn.aopExample2.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
@Slf4j
public class LoggingAspect2 {
    @Pointcut("execution(* com.mclabs.learn.aopExample2.domain.*.*set*(..))")
    public void allSetters() {

    }
    @Around("allSetters()")
    public Object log (ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();
        log.info("Call method >>>> " + methodName + " with arg " + methodArgs[0]);
        Object result = thisJoinPoint.proceed();
        log.info("Method >>> " + methodName + " returns " + result);
        return result;
    }
}
