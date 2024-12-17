package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.compositeKeys.BookingAmenityID;
import com.nguyenducmanh.hotel_app_spring_api.entities.BookingAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingAmenityRepository extends JpaRepository<BookingAmenity, BookingAmenityID> {
}
