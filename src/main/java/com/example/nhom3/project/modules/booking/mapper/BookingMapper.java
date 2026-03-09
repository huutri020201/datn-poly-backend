package com.example.nhom3.project.modules.booking.mapper;


import com.example.nhom3.project.modules.booking.dto.BookingRequestDTO;
import com.example.nhom3.project.modules.booking.dto.response.BookingResponseDTO;
import com.example.nhom3.project.modules.booking.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toEntity(BookingRequestDTO requestDTO);
    BookingResponseDTO toResponseDTO(Booking booking);
}