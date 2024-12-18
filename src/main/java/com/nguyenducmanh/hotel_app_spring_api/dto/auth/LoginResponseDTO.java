package com.nguyenducmanh.hotel_app_spring_api.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponseDTO {
    String accessToken;
    UserInfoDTO userInfo;
}
