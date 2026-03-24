package com.example.nhom3.project.modules.booking.service;

import com.example.nhom3.project.modules.booking.dto.BookingRequestDTO;
import com.example.nhom3.project.modules.booking.dto.response.BookingResponseDTO;
import com.example.nhom3.project.modules.booking.entity.Booking;


import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);
    BookingResponseDTO getBookingById(UUID id);
    List<BookingResponseDTO> getMyBookings();
    List<BookingResponseDTO> getAllBookings();
    BookingResponseDTO updateBookingStatus(UUID id, String status);
    void deleteBooking(UUID id);
}