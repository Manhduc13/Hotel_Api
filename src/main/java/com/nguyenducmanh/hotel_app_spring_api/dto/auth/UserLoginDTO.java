package com.nguyenducmanh.hotel_app_spring_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDTO {
    @NotBlank(message = "Username is required")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String username;
    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    String password;
}
