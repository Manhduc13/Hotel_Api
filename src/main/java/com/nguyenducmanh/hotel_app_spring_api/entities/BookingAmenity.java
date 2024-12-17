package com.nguyenducmanh.hotel_app_spring_api.entities;

import com.nguyenducmanh.hotel_app_spring_api.compositeKeys.BookingAmenityID;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "booking_amenity")
@Entity
public class BookingAmenity {
    @EmbeddedId
    BookingAmenityID bookingAmenityId;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    Booking booking;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    Room room;

    @ManyToOne
    @MapsId("amenityId")
    @JoinColumn(name = "amenity_id", referencedColumnName = "id")
    Amenity amenity;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;
}

