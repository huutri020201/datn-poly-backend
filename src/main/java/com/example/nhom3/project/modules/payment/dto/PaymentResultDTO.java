package com.example.nhom3.project.modules.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PaymentResultDTO {
    private String status;     // SUCCESS, FAILED
    private String message;
    private String transactionNo;
    private UUID orderId;
}
