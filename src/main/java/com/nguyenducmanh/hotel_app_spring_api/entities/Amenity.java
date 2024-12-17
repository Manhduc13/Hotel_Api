package com.nguyenducmanh.hotel_app_spring_api.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "amenities")
@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, unique = true, length = 50)
    @Nationalized
    String name;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    @OneToMany(mappedBy = "amenity")
    Set<BookingAmenity> bookingAmenities;
}
