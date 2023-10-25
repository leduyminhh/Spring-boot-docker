package com.leduyminh.filemgtservice.configs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAop {

    @AfterThrowing( value = "@annotation(com.leduyminh.commons.annotation.TrackTime)", throwing = "ex")
    public void logErrorOnController(JoinPoint joinPoint, Exception ex) {

    }

    @Around( value = "@annotation(com.leduyminh.commons.annotation.TrackTime)")
    public Object aroundTrackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }
}
