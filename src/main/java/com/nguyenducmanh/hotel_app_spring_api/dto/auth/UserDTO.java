package com.nguyenducmanh.hotel_app_spring_api.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String username;

    String displayName;
}
