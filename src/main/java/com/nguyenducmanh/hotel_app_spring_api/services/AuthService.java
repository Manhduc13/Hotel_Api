package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserInfoDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserRegisterDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserDTO register(UserRegisterDTO userCreateDTO);

    UserInfoDTO getUserInfo(String username);
}
