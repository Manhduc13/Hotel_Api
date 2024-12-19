package com.nguyenducmanh.hotel_app_spring_api.configuration;

import com.nguyenducmanh.hotel_app_spring_api.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {
    TokenService tokenService;

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        // Chặn lại request api từ client
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // Get Access Token từ request header
        String bearerToken = httpServletRequest.getHeader("Authorization");
        String jwtToken = null;

        // Kiểm tra xem Access Token có bắt đầu bằng Bearer không
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            jwtToken = bearerToken.substring(7);
        }

        Authentication authentication = tokenService.getAuthentication(jwtToken);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
