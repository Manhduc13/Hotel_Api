package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.compositeKeys.BookingDetailID;
import com.nguyenducmanh.hotel_app_spring_api.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, BookingDetailID> {
}
