package com.leduyminh.userservice.config.security;

import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.userservice.constant.UriMatchersConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticated = http
                .csrf().disable()
                .addFilterBefore(corsFilter, SessionManagementFilter.class)
                .httpBasic().disable().formLogin().disable()
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class).authorizeRequests()

                .antMatchers(UriMatchersConstant.AUTHORITY_URI)
                .hasAnyAuthority(BusinessCommon.ROLE_ADMIN)

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, UriMatchersConstant.PUBLIC_URI_GET)
                .permitAll()
                .antMatchers(HttpMethod.POST, UriMatchersConstant.PUBLIC_URI_POST)
                .permitAll()
                .antMatchers(HttpMethod.PUT, UriMatchersConstant.PUBLIC_URI_PUT)
                .permitAll()
                .antMatchers(HttpMethod.PATCH, UriMatchersConstant.PUBLIC_URI_PATCH)
                .permitAll()
                .antMatchers(HttpMethod.DELETE, UriMatchersConstant.PUBLIC_URI_DELETE)
                .permitAll()

                .anyRequest().authenticated();
    }
}
