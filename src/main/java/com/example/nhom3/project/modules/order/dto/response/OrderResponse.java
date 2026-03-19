package com.example.nhom3.project.modules.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor // Thêm để tránh lỗi khi mapping
@AllArgsConstructor
public class OrderResponse {
    UUID id;
    UUID userId;

    BigDecimal subTotal;
    BigDecimal discountAmount;
    BigDecimal totalAmount;
    String voucherCode;

    String status;
    String paymentStatus;
    String paymentMethod;

    String customerName;
    String phoneNumber;
    String shippingAddress;

    String paymentUrl;

    LocalDateTime createdAt;
    List<OrderItemResponse> items;
}