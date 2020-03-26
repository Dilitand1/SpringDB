package ru.litvinov;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

public class Recomend implements MethodInterceptor /*,MethodBeforeAdvice,AfterReturningAdvice*/ {
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("before");
    }

    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("after");
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("b");
        methodInvocation.proceed();
        System.out.println("a");
        return null;
    }
}
