package com.nguyenducmanh.hotel_app_spring_api.dto.rooms;

import com.nguyenducmanh.hotel_app_spring_api.enums.RoomType;
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
public class RoomDTO {
    UUID id;

    String number;

    RoomType type;

    int capacity;

    BigDecimal price;
}
