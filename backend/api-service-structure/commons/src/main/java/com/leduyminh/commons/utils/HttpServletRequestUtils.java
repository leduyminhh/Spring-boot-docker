package com.leduyminh.commons.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {

    public static final String DEFAULT_BROWSER = "NONE";
    public static final String DEFAULT_IP = "127.0.0.1";
    private static final String[] HEADERS_TO_TRY = new String[]{"X-Real-Ip", "X-FORWARDED-FOR", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    public HttpServletRequestUtils() {
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static String getClientBrowser() {
        try {
            return getClientBrowser(getRequest());
        } catch (Exception var1) {
            return "NONE";
        }
    }

    public static String getClientBrowser(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String user = browserDetails.toLowerCase();
        String browser = "";
        if (user.contains("msie")) {
            String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0].split("/")[0] + "-" + browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0].split("/")[1];
        } else if (!user.contains("opr") && !user.contains("opera")) {
            if (user.contains("chrome")) {
                browser = browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0].replace("/", "-");
            } else if (user.indexOf("mozilla/7.0") <= -1 && user.indexOf("netscape6") == -1 && user.indexOf("mozilla/4.7") == -1 && user.indexOf("mozilla/4.78") == -1 && user.indexOf("mozilla/4.08") == -1 && user.indexOf("mozilla/3") == -1) {
                if (user.contains("firefox")) {
                    browser = browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0].replace("/", "-");
                } else if (user.contains("rv")) {
                    browser = "IE";
                } else {
                    if (user.contains("windows")) {
                        return "Windows";
                    }

                    if (user.contains("mac")) {
                        return "Mac";
                    }

                    if (user.contains("x11")) {
                        return "Unix";
                    }

                    if (user.contains("android")) {
                        return "Android";
                    }

                    if (user.contains("iphone")) {
                        return "IPhone";
                    }

                    browser = "UnKnown";
                }
            } else {
                browser = "Netscape-?";
            }
        } else if (user.contains("opera")) {
            browser = browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0].split("/")[0] + "-" + browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0].split("/")[1];
        } else if (user.contains("opr")) {
            browser = browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0].replace("/", "-").replace("OPR", "Opera");
        }

        return browser;
    }

    public static String getClientIp() {
        try {
            return getClientIp(getRequest());
        } catch (Exception var1) {
            return "127.0.0.1";
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        try {
            String[] var2 = HEADERS_TO_TRY;
            int var3 = var2.length;

            String ip;
            for(int var4 = 0; var4 < var3; ++var4) {
                String header = var2[var4];
                ip = request.getHeader(header);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
                    return ip;
                }
            }

            ip = request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : request.getRemoteAddr();
            return ip;
        } catch (Exception var6) {
            return null;
        }
    }
}
