package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityDTO;
import com.nguyenducmanh.hotel_app_spring_api.entities.Amenity;
import com.nguyenducmanh.hotel_app_spring_api.repositories.AmenityRepository;
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
public class AmenityServiceImpl implements AmenityService {
    AmenityRepository amenityRepository;

    @Override
    public List<AmenityDTO> findAll() {
        // Get all amenity entities
        List<Amenity> amenities = amenityRepository.findAll();
        // Return data
        return amenities.stream().map(item -> {
            var amenityDTO = new AmenityDTO();
            amenityDTO.setId(item.getId());
            amenityDTO.setName(item.getName());
            amenityDTO.setPrice(item.getPrice());
            return amenityDTO;
        }).toList();
    }

    @Override
    public AmenityDTO findById(UUID id) {
        Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Amenity not found"));

        return AmenityDTO.builder()
                .id(amenity.getId())
                .name(amenity.getName())
                .price(amenity.getPrice())
                .build();
    }

    @Override
    public Page<AmenityDTO> searchAll(String keyword, Pageable pageable) {
        Specification<Amenity> spec = (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            // name LIKE %keyword%
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");
        };

        // Find by filter and paging, sorting
        Page<Amenity> amenities = amenityRepository.findAll(spec, pageable);

        return amenities.map(item -> {
            return AmenityDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();
        });
    }

    @Override
    public AmenityDTO create(AmenityCreateUpdateDTO amenityCreateUpdateDTO) {
        if (amenityCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Amenity is null");
        }
        // Check unique name
        Amenity existingAmenity = amenityRepository.findByName(amenityCreateUpdateDTO.getName());

        if (existingAmenity != null) {
            throw new IllegalArgumentException("Amenity name is existed");
        }

        // Convert to amenity
        Amenity amenity = new Amenity();
        amenity.setName(amenityCreateUpdateDTO.getName());
        amenity.setPrice(amenityCreateUpdateDTO.getPrice());

        // Save to database
        Amenity newAmenity = amenityRepository.save(amenity);

        return AmenityDTO.builder()
                .id(newAmenity.getId())
                .name(newAmenity.getName())
                .price(newAmenity.getPrice())
                .build();
    }

    @Override
    public AmenityDTO update(UUID id, AmenityCreateUpdateDTO amenityCreateUpdateDTO) {
        // Check null object
        if (amenityCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Amenity is null");
        }

        // Check if amenity not existing
        Amenity existingAmenity = amenityRepository.findById(id).orElse(null);

        if (existingAmenity == null) {
            throw new IllegalArgumentException("Amenity is not existed");
        }

        // Check unique name if not the same id
        Amenity existingAmenitySameName = amenityRepository.findByName(amenityCreateUpdateDTO.getName());

        if (existingAmenitySameName != null && !existingAmenitySameName.getId().equals(id)) {
            throw new IllegalArgumentException("Amenity name is existed");
        }

        // Convert to amenity to update
        existingAmenity.setName(amenityCreateUpdateDTO.getName());
        existingAmenity.setPrice(amenityCreateUpdateDTO.getPrice());

        // Save to database
        Amenity updatedAmenity = amenityRepository.save(existingAmenity);

        return AmenityDTO.builder()
                .id(updatedAmenity.getId())
                .name(updatedAmenity.getName())
                .price(updatedAmenity.getPrice())
                .build();
    }

    @Override
    public boolean delete(UUID id) {
        Amenity existingAmenity = amenityRepository.findById(id).orElse(null);
        if (existingAmenity == null) {
            throw new IllegalArgumentException("Amenity is not existed");
        }

        amenityRepository.delete(existingAmenity);

        return !amenityRepository.existsById(id);
    }
}
