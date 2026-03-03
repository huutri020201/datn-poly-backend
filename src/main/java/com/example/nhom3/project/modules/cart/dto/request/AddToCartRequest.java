package com.example.nhom3.project.modules.cart.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AddToCartRequest {
    private UUID productId;
    private UUID variantId;
    private Integer quantity;
    private BigDecimal unitPrice;
}