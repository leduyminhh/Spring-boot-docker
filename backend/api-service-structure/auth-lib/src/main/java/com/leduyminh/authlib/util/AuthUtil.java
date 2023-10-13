package com.leduyminh.authlib.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthUtil {
    public static final String AUTHORITIES_KEY = "authorities";
    private static final String KEY_AUTH = "-AUTH-TOKEN-";
    public static final String PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ORG_CODE = "OrgCode";

    protected String key;
    protected long timeoutMillis;
    protected String secretKey;
    protected RedisTemplate redisTemplate;

    public static AuthUtil build() {
        return new AuthUtil();
    }

    public AuthUtil withRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }

    public AuthUtil withSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }


    public AuthUtil withTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    public AuthUtil withKey(String key) {
        this.key = key;
        return this;
    }


    public String registryToken(String subject, Map<String, Object> claims) {
        try {
            String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + this.timeoutMillis)).signWith(SignatureAlgorithm.HS512, this.secretKey).compact();
            this.redisTemplate.opsForValue().set(getKeyRedis(token), token, this.timeoutMillis, TimeUnit.MILLISECONDS);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String isExist(String token) {
        try {
            Object tokenData = this.redisTemplate.opsForValue().get(getKeyRedis(token));
            return tokenData != null ? tokenData.toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims verify(String token) {
        try {
            String tokenAuth = isExist(token);
            if (tokenAuth != null && tokenAuth.equals(token)) {
                final Claims claims = getAllClaimsFromToken(token);
                if (claims.getExpiration().after(new Date())) {
                    return claims;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean clearCurrentToken(HttpServletRequest request, String subject) {
        try {
            String token = getCurrentToken(request);
            String tokenAuth = isExist(token);
            if (tokenAuth != null && tokenAuth.equals(token)) {
                final Claims claims = getAllClaimsFromToken(token);
                if (claims.getSubject().equals(subject)) {
                    return this.redisTemplate.delete(getKeyRedis(token));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCurrentToken(HttpServletRequest request) {
        try {
            return request.getHeader(AUTHORIZATION).replace(PREFIX, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getKeyRedis(String keyRequest) {
        return key + KEY_AUTH + keyRequest;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    public Claims getAllClaimsCurrent() {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(getCurrentToken(ContextHolderUtil.getRequest())).getBody();
    }
}
