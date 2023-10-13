package com.leduyminh.userservice.config.aop;

import com.leduyminh.tracking.utils.TrackingHistoryUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AopConfig {
//    @Autowired
//    TrackingUserService trackingUserService;

    @Autowired
    TrackingHistoryUtils trackingHistoryUtils;

    @AfterThrowing( value = "@annotation(com.leduyminh.commons.annotation.TrackTime)", throwing = "ex")
    public void logErrorOnController(JoinPoint joinPoint, Exception ex) {
//        TrackingDocument trackingDocument = trackingHistoryUtils.getObject(joinPoint, LogLevelEnum.ERROR, 0L, ex, TrackingDocument.class);
//        trackingDocumentService.createTrackingDocumentHistory(trackingDocument);
    }

    @Around("@annotation(com.leduyminh.commons.annotation.TrackTime)")
    public Object aroundTracking(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object val = joinPoint.proceed();
        long timeRequest = System.currentTimeMillis() - startTime;
        try {
//            TrackingDocument trackingDocument = trackingHistoryUtils.getObject(joinPoint, LogLevelEnum.INFO, timeRequest, null, TrackingDocument.class);
//            trackingDocumentService.createTrackingDocumentHistory(trackingDocument);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return val;
    }
}
