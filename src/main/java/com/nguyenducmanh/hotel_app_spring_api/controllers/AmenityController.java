package com.nguyenducmanh.hotel_app_spring_api.controllers;

import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.AmenityDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.CustomPagedResponse;
import com.nguyenducmanh.hotel_app_spring_api.services.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Amenities")
public class AmenityController {
    AmenityService amenityService;

    PagedResourcesAssembler<AmenityDTO> pagedResourcesAssembler;


    @GetMapping
    @Operation(summary = "Get all amenities")
    @ApiResponse(responseCode = "200", description = "Get all amenities successfully")
    public ResponseEntity<List<AmenityDTO>> findAll() {
        List<AmenityDTO> amenities = amenityService.findAll();
        return ResponseEntity.ok(amenities);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get amenity by id")
    @ApiResponse(responseCode = "200", description = "Get amenity by id successfully")
    @ApiResponse(responseCode = "404", description = "Amenity not found")
    public ResponseEntity<AmenityDTO> findById(@PathVariable("id") UUID id) {
        AmenityDTO amenityDTO = amenityService.findById(id);
        if (amenityDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(amenityDTO);
    }

    @GetMapping("/search")
    @Operation(summary = "Search amenities")
    @ApiResponse(responseCode = "200", description = "Search amenities successfully")
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
    @Operation(summary = "Create amenity")
    @ApiResponse(responseCode = "200", description = "Create amenity successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
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
    @Operation(summary = "Update amenity")
    @ApiResponse(responseCode = "200", description = "Update amenity successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
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
    @Operation(summary = "Delete amenity by id")
    @ApiResponse(responseCode = "200", description = "Delete amenity by id successfully")
    @ApiResponse(responseCode = "404", description = "Amenity not found")
    public ResponseEntity<Boolean> delete(
            @PathVariable("id") UUID id) {
        boolean result = amenityService.delete(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
