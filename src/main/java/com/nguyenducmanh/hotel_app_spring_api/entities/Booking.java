package com.nguyenducmanh.hotel_app_spring_api.entities;

import com.nguyenducmanh.hotel_app_spring_api.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.TimeZoneColumn;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings")
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @TimeZoneColumn
    @Column(nullable = false, columnDefinition = "DATETIMEOFFSET")
    ZonedDateTime bookingDate;

    @TimeZoneColumn
    @Column(columnDefinition = "DATETIMEOFFSET")
    ZonedDateTime checkinDate;

    @TimeZoneColumn
    @Column(columnDefinition = "DATETIMEOFFSET")
    ZonedDateTime checkoutDate;

    @Column(nullable = false)
    BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    Uzer customer;

    @OneToMany(mappedBy = "booking")
    Set<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "booking")
    Set<BookingAmenity> bookingAmenities;
}
