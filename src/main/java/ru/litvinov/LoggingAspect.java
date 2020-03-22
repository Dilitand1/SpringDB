package ru.litvinov;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    Logger logger = Logger.getLogger("1");

    @Pointcut("execution(* *.logEvent())")
    private void allLogEventMethods(){

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
