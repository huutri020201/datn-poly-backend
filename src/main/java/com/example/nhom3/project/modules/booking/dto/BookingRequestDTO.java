package com.example.nhom3.project.modules.booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class BookingRequestDTO {
    private UUID userId;
    private UUID pitchId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}