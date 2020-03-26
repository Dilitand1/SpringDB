package ru.litvinov;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect2 {

    @Pointcut("execution (* ru.litvinov.Main2.*Event*(..))")
    public void mainWithEvents(){
    }

    @Around("mainWithEvents()")
    public void testMethod(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("aaaaaa");
        System.out.println(jp.getSignature());
        jp.proceed();
        System.out.println("bbbbbbb");
    }
}
