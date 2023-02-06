package com.leduyminh.apigateway.config;

import com.leduyminh.apigateway.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public JwtTokenUtil tokenUtil() {
        return new JwtTokenUtil();
    }
}
