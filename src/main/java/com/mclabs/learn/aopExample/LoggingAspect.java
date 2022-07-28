package com.mclabs.learn.aopExample;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class LoggingAspect {

//    the execution of any method name ending with Passenger,
//    may receive any argument,
//    may return anything and
//    may contain any package

    @Before("execution(* *.*Passenger(..))")
    public void before() {
        log.info("entering method");
    }

    @After("execution(* *.*Passenger(..))")
    public void after() {
        log.info("Exiting method");
    }

    @Around("execution(* *.*Passenger(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] methodArgs = proceedingJoinPoint.getArgs();
        log.info("call method = {} with args = {} ", methodName, methodArgs);
        Object result = proceedingJoinPoint.proceed();
        log.info("method " + methodName + " return "+ result);
        return result;
    }
}
