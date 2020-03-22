package ru.litvinov;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    Logger logger = Logger.getLogger("1");

    @Pointcut("execution(* *.logEvent())")
    private void allLogEventMethods(){

    }

    @Pointcut("execution(public * ru.litvinov.Main2.*(..))")
    private void allMainMethods(){

    }

    @Before("allMainMethods()")
    private void test(JoinPoint joinPoint){
        logger.log(Level.INFO,joinPoint.getTarget().getClass().getSimpleName() + " "
                + new Date().toString()
                + " " + joinPoint.getSignature().getName());
    }

    @Before("allLogEventMethods()")
    private void logBefore(JoinPoint joinPoint){
        logger.log(Level.INFO,joinPoint.getTarget().getClass().getSimpleName() + " "
                + new Date().toString()
                + " " + joinPoint.getSignature().getName());
    }

    @AfterReturning("allLogEventMethods()")
    private void logAfter(JoinPoint joinPoint){
        logger.log(Level.INFO,joinPoint.getTarget().getClass().getSimpleName() + " "
                + new Date().toString()
                + " " + joinPoint.getSignature().getName());
    }
}
