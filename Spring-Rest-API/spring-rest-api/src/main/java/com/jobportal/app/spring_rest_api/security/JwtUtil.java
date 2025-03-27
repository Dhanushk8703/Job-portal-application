package com.jobportal.app.spring_rest_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jobportal.app.spring_rest_api.model.Role;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    // 🔹 Generate JWT Access Token (Valid for 1 Day)
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Generate Refresh Token (Valid for 7 Days)
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 7))) // 7 days expiry
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Extract Username (Email) from JWT
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    // 🔹 Extract Role from JWT
    public String getRoleFromToken(String token) {
        Object role = extractClaims(token).get("role");
        return (role != null) ? role.toString() : null;
    }

    // 🔹 Validate Token
    public boolean isValidToken(String token, String email) {
        try {
            return (getUsernameFromToken(token).equals(email) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    // 🔹 Check if Token is Expired
    public boolean isTokenExpired(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    // 🔹 Extract Claims from JWT
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
