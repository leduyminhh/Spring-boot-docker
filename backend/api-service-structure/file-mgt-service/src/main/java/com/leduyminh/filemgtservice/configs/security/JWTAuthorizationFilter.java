package com.leduyminh.filemgtservice.configs.security;

import com.leduyminh.authlib.util.AuthUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String PREFIX = "Bearer ";
    @Autowired
    protected AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (checkJWTToken(request, response)) {
                try {
                    String authToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(PREFIX, "");
                    Claims claims = authUtil.getAllClaimsFromToken(authToken);
                    List<String> roles = claims.get(AuthUtil.AUTHORITIES_KEY, List.class);
                    List authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                    e.printStackTrace();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

}
