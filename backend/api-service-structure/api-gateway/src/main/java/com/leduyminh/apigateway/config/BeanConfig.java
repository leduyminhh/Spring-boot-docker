package com.leduyminh.apigateway.config;

import com.leduyminh.apigateway.config.security.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public JwtTokenUtils tokenUtil() {
        return new JwtTokenUtils();
    }
}
