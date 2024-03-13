package com.leduyminh.apigateway.config.;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class BrowserCheckFilter implements WebFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    Environment env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        boolean isRequestSDK = Arrays.stream(UriMatchersConstant.SDK_URI).anyMatch((uri) -> request.getPath().toString().startsWith(uri));
        if(!isRequestSDK){
            String browser = exchange.getRequest().getHeaders().get("user-agent").get(0);
            Document document = getConfig();
            String brCheck = getClientBrowser(browser);
            if (document != null && document.getList("browsers", String.class) != null) {
                if (!StringUtils.isNullOrEmpty(brCheck)) {
                    try{
                        List<String> brConfig = document.getList("browsers", String.class).stream().filter(br-> !StringUtils.isNullOrEmpty(br)).map(br -> br.toLowerCase(Locale.ROOT)).collect(Collectors.toList());
                        if (brConfig.contains(brCheck.toLowerCase(Locale.ROOT))) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
                            return Mono.empty();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return chain.filter(exchange);
    }

    private Document getConfig() {
        try {
            return Document.parse(redisTemplate.opsForValue().get(env.getProperty("redis.config.key")).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private static String getClientBrowser(String browserDetails) {
        String user = browserDetails.toLowerCase();
        String browser = "";
        if (user.contains("msie")) {
            browser = "internet explorer";
        } else if (user.contains("safari") && user.contains("version")) {
            browser = "safari";
        }else if(user.contains("edg")){
            browser = "edge";
        } else if (!user.contains("opr") && !user.contains("opera")) {
            if (user.contains("chrome")) {
                browser = "chrome";
            } else if (user.indexOf("mozilla/7.0") <= -1 && user.indexOf("netscape6") == -1 && user.indexOf("mozilla/4.7") == -1 && user.indexOf("mozilla/4.78") == -1 && user.indexOf("mozilla/4.08") == -1 && user.indexOf("mozilla/3") == -1) {
                if (user.contains("firefox")) {
                    browser = "firefox";
                } else if (user.contains("rv")) {
                    browser = "IE";
                } else {
                    if (user.contains("windows")) {
                        return "windows";
                    }

                    if (user.contains("mac")) {
                        return "mac";
                    }

                    if (user.contains("x11")) {
                        return "unix";
                    }

                    if (user.contains("android")) {
                        return "android";
                    }

                    if (user.contains("iphone")) {
                        return "iphone";
                    }

                    browser = "";
                }
            } else {
                browser = "Netscape-?";
            }
        } else if (user.contains("opera")) {
            browser = "opera";
        } else if (user.contains("opr")) {
            browser = "opera";
        }

        return browser;
    }

}
 