package com.leduyminh.metricsserver.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAop {
//    @AfterThrowing(pointcut = "execution(* com..c13.controllers.*.*(..))", throwing = "ex")
//    public void logErrorOnController(JoinPoint joinPoint, Exception ex) {
//        LogUtils.logErrorOnController(joinPoint, ex);
//    }
//
//    @Around("@annotation(com.commons.annotations.TrackTime)")
//    public Object aroundTrackTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        return LogUtils.aroundTrackTime(joinPoint);
//    }
}
