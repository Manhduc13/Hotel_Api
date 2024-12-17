package com.nguyenducmanh.hotel_app_spring_api.entities;

import com.nguyenducmanh.hotel_app_spring_api.compositeKeys.BookingDetailID;
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
@Table(name = "booking_details")
@Entity
public class BookingDetail {
    @EmbeddedId
    BookingDetailID bookingDetailID;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    Room room;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    Booking booking;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;
}
