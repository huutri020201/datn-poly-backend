package com.example.nhom3.project.modules.product.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantResponse {
    UUID id;
    String sku;
    int stockQty;
    Map<String, Object> attributes;
    BigDecimal priceOverride;
    String imageUrl;
    boolean isActive;
}
