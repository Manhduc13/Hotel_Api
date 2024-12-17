package com.nguyenducmanh.hotel_app_spring_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmenityDTO {
    UUID id;

    String name;

    BigDecimal price;
}
