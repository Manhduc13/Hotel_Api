package com.nguyenducmanh.hotel_app_spring_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${app.security.access-token-secret-key}")
    String key;
    @Value("${app.security.access-token-expired-in-second}")
    Integer expireTime;

    @Override
    public String generateToken(Authentication authentication) {
        return "";
    }

    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }
}
