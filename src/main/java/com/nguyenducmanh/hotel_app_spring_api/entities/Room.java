package com.nguyenducmanh.hotel_app_spring_api.entities;

import com.nguyenducmanh.hotel_app_spring_api.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rooms")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(unique = true, nullable = false)
    String number;

    @Column(nullable = false)
    RoomType type;

    int capacity;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    @OneToMany(mappedBy = "room")
    Set<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "room")
    Set<BookingAmenity> bookingAmenities;
}
