package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.entities.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AmenityRepository extends JpaRepository<Amenity, UUID>, JpaSpecificationExecutor<Amenity> {
    Amenity findByName(String name);
}
