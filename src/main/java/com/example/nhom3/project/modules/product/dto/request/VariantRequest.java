package com.example.nhom3.project.modules.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantRequest {
    @NotBlank(message = "SKU không được để trống")
    String sku;

    @Min(value = 0, message = "Số lượng kho ban đầu không được nhỏ hơn 0")
    int stockQty;

    @JdbcTypeCode(SqlTypes.JSON)
    Map<String, Object> attributes;

    BigDecimal priceOverride;

    String imageUrl;

    Boolean isActive = true;
}
