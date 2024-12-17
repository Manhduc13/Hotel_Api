package com.nguyenducmanh.hotel_app_spring_api.dto;

import com.nguyenducmanh.hotel_app_spring_api.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCreateUpdateDTO {
    @NotNull(message = "Booking date is required")
    ZonedDateTime bookingDate;

    ZonedDateTime checkinDate;

    ZonedDateTime checkoutDate;

    @NotNull(message = "Booking status is required")
    BookingStatus status;
}
