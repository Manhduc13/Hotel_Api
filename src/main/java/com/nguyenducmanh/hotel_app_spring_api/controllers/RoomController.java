package com.nguyenducmanh.hotel_app_spring_api.controllers;

import com.nguyenducmanh.hotel_app_spring_api.dto.CustomPagedResponse;
import com.nguyenducmanh.hotel_app_spring_api.dto.RoomCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.RoomDTO;
import com.nguyenducmanh.hotel_app_spring_api.services.RoomService;
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
@RequestMapping("/api/rooms")
public class RoomController {
    RoomService roomService;
    PagedResourcesAssembler<RoomDTO> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable("id") UUID id) {
        RoomDTO roomDTO = roomService.findById(id);
        if (roomDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", defaultValue = "number") String orderBy,
            @RequestParam(value = "sortBy", defaultValue = "acs") String sortBy
    ) {
        Pageable pageable = null;
        if (sortBy.equals("acs")) {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        }
        Page<RoomDTO> roomDTOs = roomService.searchAll(keyword, pageable);
        PagedModel<EntityModel<RoomDTO>> pagedModel = this.pagedResourcesAssembler.toModel(roomDTOs);

        Collection<EntityModel<RoomDTO>> data = pagedModel.getContent();
        PagedModel.PageMetadata pageInfo = pagedModel.getMetadata();
        Links links = pagedModel.getLinks();

        CustomPagedResponse<EntityModel<RoomDTO>> response = new CustomPagedResponse<>(data, pageInfo, links);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@RequestBody @Valid RoomCreateUpdateDTO roomCreateUpdateDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        RoomDTO roomDTO = roomService.create(roomCreateUpdateDTO);
        if (roomDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable("id") UUID id, @RequestBody @Valid RoomCreateUpdateDTO roomCreateUpdateDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        RoomDTO roomDTO = roomService.update(id, roomCreateUpdateDTO);
        if (roomDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
        boolean result = roomService.delete(id);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
