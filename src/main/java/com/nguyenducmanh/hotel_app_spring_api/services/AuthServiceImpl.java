package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserInfoDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.auth.UserRegisterDTO;
import com.nguyenducmanh.hotel_app_spring_api.entities.Uzer;
import com.nguyenducmanh.hotel_app_spring_api.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzer user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> authorities = Set.of();
        // Check if user has roles and map them to authorities
        if (user.getRoles() != null) {
            authorities = user.getRoles().stream()
                    .map(role -> "ROLE_" + role.getName())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        // Return a UserDetails object with username, password and authorities (empty if
        // no roles)
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDTO register(UserRegisterDTO request) {
        // Check null obj
        if (request == null) {
            throw new IllegalArgumentException("Register request is null");
        }
        // Check existed user
        Uzer existingUsers = userRepository.findByUsernameOrPhoneNumberOrEmail(request.getUsername(), request.getPhoneNumber(), request.getEmail());
        if (existingUsers != null && !existingUsers.getUsername().equals(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (existingUsers != null && !existingUsers.getPhoneNumber().equals(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone already exists");
        }
        if (existingUsers != null && !existingUsers.getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Check password and confirm password
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password not match");
        }
        // Create new User
        Uzer newUsers = Uzer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        // Save to db
        Uzer savedUsers = userRepository.save(newUsers);

        return UserDTO.builder()
                .firstName(savedUsers.getFirstName())
                .lastName(savedUsers.getLastName())
                .username(savedUsers.getUsername())
                .phoneNumber(savedUsers.getPhoneNumber())
                .email(savedUsers.getEmail())
                .displayName(savedUsers.getDisplayName())
                .build();
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        Uzer user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            roles.add(role.getName().toString());
        });
        return UserInfoDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .roles(roles)
                .build();
    }
}
