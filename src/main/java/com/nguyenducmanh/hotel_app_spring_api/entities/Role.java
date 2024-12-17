package com.nguyenducmanh.hotel_app_spring_api.entities;

import com.nguyenducmanh.hotel_app_spring_api.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, unique = true)
    RoleName name;

    @Column(length = 500)
    String description;

    @ManyToMany(mappedBy = "roles")
    Set<Uzer> users;
}
