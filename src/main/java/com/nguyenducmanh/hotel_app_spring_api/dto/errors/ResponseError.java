package com.nguyenducmanh.hotel_app_spring_api.dto.errors;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseError {
    String message;
    HttpStatus status;
}
