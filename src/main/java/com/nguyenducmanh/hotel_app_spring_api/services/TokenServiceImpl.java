package com.nguyenducmanh.hotel_app_spring_api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${app.security.access-token-secret-key}")
    private String secretKey;
    @Value("${app.security.access-token-expired-in-second}")
    private Integer expireTime;

    @Override
    public String generateToken(Authentication authentication) {
        // Admin,User
        String roles = authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(","));
        return generateAccessToken(authentication.getName(), roles);
    }

    private String generateAccessToken(String name, String roles) {
        // Now + 3600s from application.properties => expiredAt = Now + 1h
        // Lay ra thoi diem het han cua Access Token
        LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(expireTime);

        // Decode secretKey from Base64 to SecretKey
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Convert LocalDateTime to Date

        Date expiration = Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant());
        // Generate JWT token
        return Jwts.builder()
                .subject(name)
                .claim("roles", roles)
                .claim("something", "i don't know")
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        if (token == null) {
            return null;
        }
        // Decode secretKey from Base64 to SecretKey
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String roles = claims.get("roles").toString();

            Set<GrantedAuthority> authorities = Set.of(roles.split(",")).stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

            User principle = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principle, token, authorities);
        } catch (Exception e) {
            return null;
        }
    }
}
