package com.mclabs.learn.aopExample2.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
@Slf4j
public class LoggingAspect1 {

    @Before("execution(public String com.mclabs.learn.aopExample2.domain.Flight.getId())")
    public void loggingAdviceGetId() {
        log.info("flight GetId method will be called");
    }

    @AfterReturning("execution(public * *.print())")
    public void loggingAdvicePrint() {
        log.info("A print method has been called");
    }
}
