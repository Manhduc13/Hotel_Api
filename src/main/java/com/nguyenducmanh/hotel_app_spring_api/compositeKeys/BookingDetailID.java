package com.nguyenducmanh.hotel_app_spring_api.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailID implements Serializable {
    @Column(nullable = false)
    UUID bookingId;
    @Column(nullable = false)
    UUID roomId;
}
