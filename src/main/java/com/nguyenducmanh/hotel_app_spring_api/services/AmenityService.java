package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.amenities.AmenityCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.amenities.AmenityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AmenityService {
    List<AmenityDTO> findAll();

    AmenityDTO findById(UUID id);

    Page<AmenityDTO> searchAll(String keyword, Pageable pageable);

    AmenityDTO create(AmenityCreateUpdateDTO amenityCreateUpdateDTO);

    AmenityDTO update(UUID id, AmenityCreateUpdateDTO amenityCreateUpdateDTO);

    boolean delete(UUID id);
}
