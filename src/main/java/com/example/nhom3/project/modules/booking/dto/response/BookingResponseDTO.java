package com.example.nhom3.project.modules.booking.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID pitchId;
    private LocalDateTime createdAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal totalPrice;
    private String status;
}