package com.leduyminh.commons.utils;

public class StringUtil {

    public static boolean isNullOrEmpty(final String s) {
        if (s == null || s.isEmpty())
            return true;
        return false;
    }
}
