package com.dam.restoreserve_api.Config;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtService {

    private final String SECRET = "iRCXv3v5jmD90pkugE9Kib1RvCULr9hQlgHssh42jIy";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

     public String generateToken(String username) {
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 86400000))//24h
        .signWith(key)
        .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
