package com.nguyenducmanh.hotel_app_spring_api.services;

import com.nguyenducmanh.hotel_app_spring_api.dto.bookings.BookingCreateUpdateDTO;
import com.nguyenducmanh.hotel_app_spring_api.dto.bookings.BookingDTO;
import com.nguyenducmanh.hotel_app_spring_api.entities.Booking;
import com.nguyenducmanh.hotel_app_spring_api.enums.BookingStatus;
import com.nguyenducmanh.hotel_app_spring_api.repositories.BookingRepository;
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
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;

    @Override
    public List<BookingDTO> findAll() {
        // Get all amenity entities
        List<Booking> bookings = bookingRepository.findAll();
        // Return data
        return bookings.stream().map(item -> {
            return BookingDTO.builder()
                    .id(item.getId())
                    .bookingDate(item.getBookingDate())
                    .checkinDate(item.getCheckinDate())
                    .checkoutDate(item.getCheckoutDate())
                    .status(item.getStatus())
                    .build();
        }).toList();
    }

    @Override
    public BookingDTO findById(UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        return BookingDTO.builder()
                .id(booking.getId())
                .bookingDate(booking.getBookingDate())
                .checkinDate(booking.getCheckinDate())
                .checkoutDate(booking.getCheckoutDate())
                .status(booking.getStatus())
                .build();
    }

    @Override
    public Page<BookingDTO> searchAll(String keyword, Pageable pageable) {
        Specification<Booking> spec = (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            // name LIKE %keyword%
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("bookingDate")),
                    "%" + keyword.toLowerCase() + "%");
        };

        // Find by filter and paging, sorting
        Page<Booking> bookings = bookingRepository.findAll(spec, pageable);


        return bookings.map(item -> {
            return BookingDTO.builder()
                    .id(item.getId())
                    .bookingDate(item.getBookingDate())
                    .checkinDate(item.getCheckinDate())
                    .checkoutDate(item.getCheckoutDate())
                    .status(item.getStatus())
                    .build();
        });
    }

    @Override
    public BookingDTO create(BookingCreateUpdateDTO bookingCreateUpdateDTO) {
        if (bookingCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Booking is null");
        }

        Booking booking = Booking.builder()
                .bookingDate(bookingCreateUpdateDTO.getBookingDate())
                .checkinDate(null)
                .checkoutDate(null)
                .status(BookingStatus.PENDING)
                .build();

        // Save to database
        Booking newBooking = bookingRepository.save(booking);

        return BookingDTO.builder()
                .id(newBooking.getId())
                .bookingDate(newBooking.getBookingDate())
                .checkinDate(newBooking.getCheckinDate())
                .checkoutDate(newBooking.getCheckoutDate())
                .status(newBooking.getStatus())
                .build();
    }

    @Override
    public BookingDTO update(UUID id, BookingCreateUpdateDTO bookingCreateUpdateDTO) {
        if (bookingCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Booking is null");
        }

        Booking existing = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        existing.setBookingDate(bookingCreateUpdateDTO.getBookingDate());
        existing.setCheckinDate(bookingCreateUpdateDTO.getCheckinDate());
        existing.setCheckoutDate(bookingCreateUpdateDTO.getCheckoutDate());
        existing.setStatus(bookingCreateUpdateDTO.getStatus());

        // Save to database
        Booking updatedBooking = bookingRepository.save(existing);

        return BookingDTO.builder()
                .id(updatedBooking.getId())
                .bookingDate(updatedBooking.getBookingDate())
                .checkinDate(updatedBooking.getCheckinDate())
                .checkoutDate(updatedBooking.getCheckoutDate())
                .status(updatedBooking.getStatus())
                .build();
    }

    @Override
    public boolean delete(UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        bookingRepository.delete(booking);
        return !bookingRepository.existsById(id);
    }
}
