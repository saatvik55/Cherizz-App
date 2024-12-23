package com.gratitude.gratitude_photodiary.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    static String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    private final long jwtExpirationMs = 1000 * 60 * 60 * 10;

    public String generateToken(String username) {
        System.out.println("Base64 Secret Key: " + base64Key);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false; // Invalid token
        }
    }
}
