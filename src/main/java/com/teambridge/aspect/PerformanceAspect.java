package com.teambridge.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
  Advice -- return method execution time
 */

@Aspect
@Component
@Slf4j // logger object to log information on the console
public class PerformanceAspect {

    @Pointcut("@annotation(com.teambridge.annotation.ExecutionTime)")
    public void executionTimePC() {}

    @Around("executionTimePC()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = null;
        log.info("Execution starts: ");

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        log.info("Execution time: {} ms - Method: {}", end - start, joinPoint.getSignature().toShortString());

        return result;
    }
}
