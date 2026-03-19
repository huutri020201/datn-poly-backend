package com.example.nhom3.project.modules.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PaymentDTO {
    private String paymentUrl; // Link QR cho khách
    private UUID orderId;
    private BigDecimal amount;
}
