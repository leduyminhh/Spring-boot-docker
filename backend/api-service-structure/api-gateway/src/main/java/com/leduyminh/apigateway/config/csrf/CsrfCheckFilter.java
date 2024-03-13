package com.leduyminh.apigateway.config.csrf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CsrfCheckFilter implements WebFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
       CsrfStatusEnums csrfStatus = CsrfUtil.verifyCsrfToken(redisTemplate, exchange);
        if (CsrfStatusEnums.Invalid.equals(csrfStatus) || CsrfStatusEnums.Not_found.equals(csrfStatus)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

}
 