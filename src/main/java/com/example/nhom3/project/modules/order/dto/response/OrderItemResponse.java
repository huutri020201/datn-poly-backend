package com.example.nhom3.project.modules.order.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long id;
    UUID variantId;
    String productName;
    String sku;
    BigDecimal priceAtPurchase;
    Integer quantity;
    private String imageSnapshot;
    Map<String, Object> attributesSnapshot;
}
