package com.leduyminh.filemgtservice.configs.security;

import com.leduyminh.filemgtservice.configs.CustomAccessDeniedHandler;
import com.leduyminh.filemgtservice.configs.CustomauthenticationEntryPointHandler;
import com.leduyminh.filemgtservice.constant.UriMatchersConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    CorsFilter corsFilter;
    @Autowired
    Environment evn;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(corsFilter, SessionManagementFilter.class)
                .httpBasic().disable().formLogin().disable()
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class).authorizeRequests()
                .antMatchers(UriMatchersConstant.PUBLIC_URI).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/public-resource/**").permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomauthenticationEntryPointHandler();
    }


}
