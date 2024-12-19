package com.nguyenducmanh.hotel_app_spring_api.services;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication authentication);

    Authentication getAuthentication(String token);
}
