package com.example.nhom3.project.modules.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PitchResponseDTO {
    private UUID id;
    private String name;
    private BigDecimal pricePerHour;
}