package com.nguyenducmanh.hotel_app_spring_api.controllers;

import com.nguyenducmanh.hotel_app_spring_api.dto.BookingCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.BookingDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.CustomPagedResponse;
import com.nguyenducmanh.hotel_app_spring_api.services.BookingService;
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
@RequestMapping("/api/bookings")
public class BookingController {
    BookingService bookingService;
    PagedResourcesAssembler<BookingDTO> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        List<BookingDTO> bookings = bookingService.findAll();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") UUID id) {
        BookingDTO bookingDTO = bookingService.findById(id);
        if (bookingDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAll(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "bookingDate") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order
    ) {
        Pageable pageable = null;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        Page<BookingDTO> bookingDTOs = bookingService.searchAll(keyword, pageable);
        PagedModel<EntityModel<BookingDTO>> pagedModel = this.pagedResourcesAssembler.toModel(bookingDTOs);

        Collection<EntityModel<BookingDTO>> data = pagedModel.getContent();
        PagedModel.PageMetadata pageInfo = pagedModel.getMetadata();
        Links links = pagedModel.getLinks();

        CustomPagedResponse<EntityModel<BookingDTO>> response = new CustomPagedResponse<>(data, pageInfo, links);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(
            @RequestBody @Valid BookingCreateUpdateDTO bookingCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        BookingDTO bookingDTO = bookingService.create(bookingCreateUpdateDTO);

        if (bookingDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookingDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid BookingCreateUpdateDTO bookingCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        BookingDTO bookingDTO = bookingService.update(id, bookingCreateUpdateDTO);

        if (bookingDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable("id") UUID id) {
        boolean result = bookingService.delete(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
