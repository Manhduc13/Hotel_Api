package com.nguyenducmanh.hotel_app_spring_api.controllers;

import com.nguyenducmanh.hotel_app_spring_api.dto.auth.*;
import com.nguyenducmanh.hotel_app_spring_api.services.AuthService;
import com.nguyenducmanh.hotel_app_spring_api.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentications")
public class AuthController {
    AuthService authService;
    TokenService tokenService;
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/register")
    @Operation(summary = "Register")
    @ApiResponse(responseCode = "200", description = "Register user successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserRegisterDTO request,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        UserDTO userDTO = authService.register(request);
        if (userDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200", description = "Login successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO request,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenService.generateToken(authentication);
        UserInfoDTO userInfoDTO = authService.getUserInfo(request.getUsername());

        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .accessToken(accessToken)
                .userInfo(userInfoDTO)
                .build();
        return ResponseEntity.ok(responseDTO);
    }
}
