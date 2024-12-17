package com.nguyenducmanh.hotel_app_spring_api.dto;

import com.nguyenducmanh.hotel_app_spring_api.enums.BookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDTO {
    UUID id;

    ZonedDateTime bookingDate;

    ZonedDateTime checkinDate;

    ZonedDateTime checkoutDate;

    BookingStatus status;
}
