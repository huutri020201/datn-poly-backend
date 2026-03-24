package com.example.nhom3.project.modules.booking.controller;

import com.example.nhom3.project.modules.booking.dto.BookingRequestDTO;
import com.example.nhom3.project.modules.booking.dto.response.BookingResponseDTO;
import com.example.nhom3.project.modules.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        BookingResponseDTO response = bookingService.createBooking(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}