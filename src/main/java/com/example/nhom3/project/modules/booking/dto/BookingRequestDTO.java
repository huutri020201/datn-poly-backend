package com.example.nhom3.project.modules.booking.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingRequestDTO {
    private UUID userId;
    private UUID pitchId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}