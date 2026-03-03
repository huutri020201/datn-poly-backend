package com.example.nhom3.project.modules.product.dto.response;

import com.example.nhom3.project.modules.product.dto.request.VariantRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    UUID id;
    String name;
    String slug;
    BigDecimal basePrice;
    String description;
    String brandName;
    String categoryName;
    List<VariantRequest> variants;
}
