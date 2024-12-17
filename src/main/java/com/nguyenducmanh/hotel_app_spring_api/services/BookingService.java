package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.BookingCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<BookingDTO> findAll();

    BookingDTO findById(UUID id);

    Page<BookingDTO> searchAll(String keyword, Pageable pageable);

    BookingDTO create(BookingCreateUpdateDTO bookingCreateUpdateDTO);

    BookingDTO update(UUID id, BookingCreateUpdateDTO bookingCreateUpdateDTO);

    boolean delete(UUID id);
}
