package com.nguyenducmanh.hotel_app_spring_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDTO {
    @NotBlank(message = "First name is required")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String firstName;

    @NotBlank(message = "Last name is required")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String lastName;

    @NotBlank(message = "Phone is required")
    @Length(min = 10, max = 12, message = "Phone must be between 10 and 12 characters")
    String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid format")
    String email;

    @NotBlank(message = "Username is required")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String username;

    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    String password;

    @NotBlank(message = "Confirm password is required")
    @Length(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    String confirmPassword;


}
