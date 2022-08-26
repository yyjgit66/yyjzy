package com.yyjzy.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author yyjzy
 * @since 2022.7.6
 */
@Slf4j
@Aspect
public class EventTrackAspect {

    // 切入点为使用的 YyjzyApplication注解的
    @Pointcut("@annotation(com.yyjzy.annotation.YyjzyApplication)")
    public void startAspectse() {
    }

    //前置通知
    @Before("execution(* **(..))")
    public void before(JoinPoint pjp) {
         log.info("yyjzy start------>>>>");

    }

}
