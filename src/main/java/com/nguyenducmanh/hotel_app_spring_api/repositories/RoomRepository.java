package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
    Room findByNumber(String number);
}
