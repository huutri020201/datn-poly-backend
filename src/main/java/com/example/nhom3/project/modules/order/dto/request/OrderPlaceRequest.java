package com.example.nhom3.project.modules.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderPlaceRequest {
    @NotBlank(message = "Tên không được để trống")
    String customerName;
    @NotBlank(message = "SĐT không được để trống")
    String phoneNumber;
    @NotBlank(message = "Địa chỉ không được để trống")
    String shippingAddress;
    String note;
    String voucherCode; // Có thể null
    String paymentMethod;
}