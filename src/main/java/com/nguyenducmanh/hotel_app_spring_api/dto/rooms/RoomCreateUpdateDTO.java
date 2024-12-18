package com.nguyenducmanh.hotel_app_spring_api.dto.rooms;

import com.nguyenducmanh.hotel_app_spring_api.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomCreateUpdateDTO {
    @NotBlank(message = "Room number is required")
    @Length(min = 2, max = 255, message = "Room number must be between 2 and 255 characters")
    String number;

    @NotNull(message = "Room type is required")
    RoomType type;

    @NotNull(message = "Room capacity is required")
    int capacity;

    @NotNull(message = "Room price is required")
    BigDecimal price;
}
