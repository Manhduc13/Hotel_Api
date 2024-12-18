package com.nguyenducmanh.hotel_app_spring_api.repositories;

import com.nguyenducmanh.hotel_app_spring_api.entities.Uzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Uzer, UUID>, JpaSpecificationExecutor<Uzer> {
    Uzer findByUsername(String username);

    Uzer findByUsernameOrPhoneNumberOrEmail(String username, String phoneNumber, String email);
}
