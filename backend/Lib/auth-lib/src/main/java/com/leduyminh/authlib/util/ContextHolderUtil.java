package com.leduyminh.authlib.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContextHolderUtil {
    public static final String GUEST_USER = "GUEST";

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static boolean validatorUserNameCurrentOrPermitRoles(String userName, List<String> rolePermits) {
        return validatorUserNameCurrent(userName) || validatorPermitRoles(rolePermits);
    }

    public static String getUserName() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } catch (Exception e) {
            return GUEST_USER;
        }
    }

    public static boolean validatorUserNameCurrent(String userName) {
        String userNameSession = getUserName();
        if (userNameSession != null && !userNameSession.isEmpty()) {
            return userNameSession.equals(userName);
        }
        return false;
    }

    public static boolean validatorPermitRoles(List<String> rolePermits) {
        return containsOneElement(getAuthorities(), rolePermits);
    }

    private static boolean containsOneElement(List<String> list1, List<String> list2) {
        return isNotNullAndEmpty(list1) && isNotNullAndEmpty(list2) && list1.stream().anyMatch(list2::contains);
    }

    private static boolean isNotNullAndEmpty(List<String> list) {
        return list != null && !list.isEmpty();
    }

    public static List<String> getAuthorities() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
