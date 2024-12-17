package com.nguyenducmanh.hotel_app_spring_api.controllers;

import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.CustomPagedResponse;
import com.nguyenducmanh.hotel_app_spring_api.services.AmenityService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    AmenityService amenityService;

    PagedResourcesAssembler<AmenityDTO> pagedResourcesAssembler;


    @GetMapping
    public ResponseEntity<List<AmenityDTO>> findAll() {
        List<AmenityDTO> amenities = amenityService.findAll();
        return ResponseEntity.ok(amenities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> findById(@PathVariable("id") UUID id) {
        AmenityDTO amenityDTO = amenityService.findById(id);
        if (amenityDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(amenityDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAll(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order
    ) {
        Pageable pageable = null;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<AmenityDTO> result = amenityService.searchAll(keyword, pageable);

        PagedModel<EntityModel<AmenityDTO>> pagedModel = pagedResourcesAssembler.toModel(result);

        // Get data, page, and links from pagedModel
        Collection<EntityModel<AmenityDTO>> data = pagedModel.getContent();
        PagedModel.PageMetadata pageInfo = pagedModel.getMetadata();
        Links links = pagedModel.getLinks();

        CustomPagedResponse<EntityModel<AmenityDTO>> response = new CustomPagedResponse<>(data, pageInfo, links);

        return ResponseEntity.ok(response);
    }

    // Create
    @PostMapping
    public ResponseEntity<AmenityDTO> create(
            @RequestBody @Valid AmenityCreateUpdateDTO amenityCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        AmenityDTO amenityDTO = amenityService.create(amenityCreateUpdateDTO);

        if (amenityDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(amenityDTO);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<AmenityDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid AmenityCreateUpdateDTO amenityCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        AmenityDTO amenityDTO = amenityService.update(id, amenityCreateUpdateDTO);

        if (amenityDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(amenityDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable("id") UUID id) {
        boolean result = amenityService.delete(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
