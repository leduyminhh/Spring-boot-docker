package com.leduyminh.tracking.utils;

import com.leduyminh.commons.annotation.TrackTime;
import com.leduyminh.commons.enums.LogLevelEnum;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.commons.utils.HttpServletRequestUtils;
import com.leduyminh.commons.utils.StringUtils;
import com.leduyminh.tracking.entity.Tracking;
import lombok.Builder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Builder
public class TrackingHistoryUtils {

    private MessageSource messageSource;
    private ModelMapper modelMapper;

    public <T> T getObject(JoinPoint joinPoint, Long timeRequest, LogLevelEnum level, Exception ex, Class<T> tClass) {
        Tracking tracking = new Tracking();

        try {
            HttpServletRequest servletRequest = HttpServletRequestUtils.getRequest();
            String ip = servletRequest.getRemoteAddr();
            String method = this.getMethod(joinPoint);
            String messageKey = this.getMethodName(joinPoint);
            tracking.setUsername(BusinessCommon.getUserName());
            tracking.setIpAddress(ip);
            tracking.setMethod(method);
            tracking.setMethodName(this.getMessage(messageKey));
            tracking.setClassName(this.getClassName(joinPoint));
            tracking.setSystem(this.getPhanHe(joinPoint));
            tracking.setLevel(level.getValue());
            tracking.setTimeRequest(timeRequest);
            tracking.setActionDate(new Date());
            if (ex != null) {
                tracking.setMessageError(ex.getMessage());
            }

            return this.modelMapper.map(tracking, tClass);
        } catch (Exception var11) {
            System.out.println("================================= Create tracking fail =================================");
            System.out.println("================================= Message:" + var11.getMessage());
            return null;
        }
    }

    private String getMessage(String messageKey) {
        return !StringUtils.isNullOrEmpty(messageKey) ? this.messageSource.getMessage(messageKey, (Object[])null, LocaleContextHolder.getLocale()) : null;
    }

    private String getPhanHe(ProceedingJoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Method[] listMethods = Class.forName(joinPoint.getTarget().getClass().getName()).getMethods();
            Method[] var4 = listMethods;
            int var5 = listMethods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method m = var4[var6];
                if (m.getName().equals(methodName)) {
                    TrackTime produce = (TrackTime)m.getAnnotation(TrackTime.class);
                    if (produce != null) {
                        return produce.value();
                    }
                    break;
                }
            }
        } catch (Exception var9) {
        }

        return "PhanHe";
    }

    private String getPhanHe(JoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Method[] listMethods = Class.forName(joinPoint.getTarget().getClass().getName()).getMethods();
            Method[] var4 = listMethods;
            int var5 = listMethods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method m = var4[var6];
                if (m.getName().equals(methodName)) {
                    TrackTime produce = (TrackTime)m.getAnnotation(TrackTime.class);
                    if (produce != null) {
                        return produce.value();
                    }
                    break;
                }
            }
        } catch (Exception var9) {
        }

        return "PhanHe";
    }

    private String getClassName(JoinPoint joinPoint) {
        try {
            return joinPoint.getTarget().getClass().getName();
        } catch (Exception var3) {
            return "com.leduyminh";
        }
    }

    private String getMethod(JoinPoint joinPoint) {
        try {
            return joinPoint.getSignature().getName();
        } catch (Exception var3) {
            return "POST";
        }
    }

    private String getMethodName(JoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Method[] listMethods = Class.forName(joinPoint.getTarget().getClass().getName()).getMethods();
            Method[] var3 = listMethods;
            int var4 = listMethods.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Method m = var3[var5];
                if (m.getName().equals(methodName)) {
                    TrackTime produce = (TrackTime)m.getAnnotation(TrackTime.class);
                    if (produce != null) {
                        return produce.methodName();
                    }
                    break;
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return "";
    }
}
