package com.nguyenducmanh.hotel_app_spring_api.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoDTO {
    UUID id;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String username;

    String displayName;

    Set<String> roles;
}
