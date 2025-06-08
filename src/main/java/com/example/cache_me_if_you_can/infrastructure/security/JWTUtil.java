package com.example.cache_me_if_you_can.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
    
    private final JWTProperties jwtProperties;
    private final SecretKey signingKey;

    public JWTUtil(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(signingKey)
            .compact();
    }

    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = parseToken(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
    }
}
