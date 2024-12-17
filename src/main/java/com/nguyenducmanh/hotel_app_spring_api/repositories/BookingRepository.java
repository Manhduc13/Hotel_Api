package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
