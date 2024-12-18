package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.rooms.RoomCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.rooms.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<RoomDTO> findAll();

    RoomDTO findById(UUID id);

    Page<RoomDTO> searchAll(String keyword, Pageable pageable);

    RoomDTO create(RoomCreateUpdateDTO roomCreateUpdateDTO);

    RoomDTO update(UUID id, RoomCreateUpdateDTO roomCreateUpdateDTO);

    boolean delete(UUID id);
}
