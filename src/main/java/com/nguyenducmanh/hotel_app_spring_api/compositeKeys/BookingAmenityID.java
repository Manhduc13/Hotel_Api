package com.nguyenducmanh.hotel_app_spring_api.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.TimeZoneColumn;

import java.time.ZonedDateTime;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingAmenityID {
    @Column(nullable = false)
    UUID bookingId;

    @Column(nullable = false)
    UUID amenityId;

    @Column(nullable = false)
    UUID roomId;

    @TimeZoneColumn
    @Column(nullable = false, columnDefinition = "DATETIMEOFFSET")
    ZonedDateTime booking_amenity_date;
}
