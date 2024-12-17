package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.RoomCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.RoomDTO;
import com.nguyenducmanh.hotel_app_spring_api.entities.Room;
import com.nguyenducmanh.hotel_app_spring_api.repositories.RoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    RoomRepository RoomRepository;

    @Override
    public List<RoomDTO> findAll() {
        // Get all amenity entities
        List<Room> rooms = RoomRepository.findAll();
        // Return data
        return rooms.stream().map(item -> {
            return RoomDTO.builder()
                    .id(item.getId())
                    .number(item.getNumber())
                    .type(item.getType())
                    .capacity(item.getCapacity())
                    .price(item.getPrice())
                    .build();
        }).toList();
    }

    @Override
    public RoomDTO findById(UUID id) {
        Room room = RoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        return RoomDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .type(room.getType())
                .capacity(room.getCapacity())
                .price(room.getPrice())
                .build();
    }

    @Override
    public Page<RoomDTO> searchAll(String keyword, Pageable pageable) {
        Specification<Room> spec = (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            // name LIKE %keyword%
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("number")),
                    "%" + keyword.toLowerCase() + "%");
        };

        // Find by filter and paging, sorting
        Page<Room> Rooms = RoomRepository.findAll(spec, pageable);


        return Rooms.map(item -> {
            return RoomDTO.builder()
                    .id(item.getId())
                    .number(item.getNumber())
                    .type(item.getType())
                    .capacity(item.getCapacity())
                    .price(item.getPrice())
                    .build();
        });
    }

    @Override
    public RoomDTO create(RoomCreateUpdateDTO RoomCreateUpdateDTO) {
        if (RoomCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Room is null");
        }

        Room room = Room.builder()
                .number(RoomCreateUpdateDTO.getNumber())
                .type(RoomCreateUpdateDTO.getType())
                .capacity(RoomCreateUpdateDTO.getCapacity())
                .price(RoomCreateUpdateDTO.getPrice())
                .build();

        // Save to database
        Room newRoom = RoomRepository.save(room);

        return RoomDTO.builder()
                .id(newRoom.getId())
                .number(newRoom.getNumber())
                .type(newRoom.getType())
                .capacity(newRoom.getCapacity())
                .price(newRoom.getPrice())
                .build();
    }

    @Override
    public RoomDTO update(UUID id, RoomCreateUpdateDTO RoomCreateUpdateDTO) {
        if (RoomCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Room is null");
        }

        Room existing = RoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        existing.setNumber(RoomCreateUpdateDTO.getNumber());
        existing.setType(RoomCreateUpdateDTO.getType());
        existing.setCapacity(RoomCreateUpdateDTO.getCapacity());
        existing.setPrice(RoomCreateUpdateDTO.getPrice());

        // Save to database
        Room updatedRoom = RoomRepository.save(existing);

        return RoomDTO.builder()
                .id(updatedRoom.getId())
                .number(updatedRoom.getNumber())
                .type(updatedRoom.getType())
                .capacity(updatedRoom.getCapacity())
                .price(updatedRoom.getPrice())
                .build();
    }

    @Override
    public boolean delete(UUID id) {
        Room Room = RoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        RoomRepository.delete(Room);
        return !RoomRepository.existsById(id);
    }
}
