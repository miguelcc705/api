// src/main/java/com/api/api/security/JwtUtils.java
package com.api.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    // public String generateToken(String username) {
    //     return Jwts.builder()
    //                .setSubject(username)
    //                .setIssuedAt(new Date())
    //                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
    //                .signWith(SignatureAlgorithm.HS512, jwtSecret)
    //                .compact();
    // }
    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    // public String getUsernameFromToken(String token) {
    //     return Jwts.parser()
    //                .setSigningKey(jwtSecret)
    //                .parseClaimsJws(token)
    //                .getBody()
    //                .getSubject();
    // }

    public String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }
    

    // public boolean validateToken(String token) {
    //     try {
    //         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    //         return true;
    //     } catch (JwtException | IllegalArgumentException e) {
    //         return false;
    //     }
    // }
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace(); // Para saber por qué falla
            return false;
        }
    }
}
