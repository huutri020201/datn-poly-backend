package com.example.nhom3.project.modules.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name;

    @NotNull(message = "Brand ID không được để trống")
    UUID brandId;

    @NotNull(message = "Category ID không được để trống")
    UUID categoryId;

    @NotNull(message = "Giá cơ bản không được để trống")
    @Min(value = 0, message = "Giá không được nhỏ hơn 0")
    BigDecimal basePrice;

    String description;

    @NotEmpty(message = "Sản phẩm phải có ít nhất một biến thể")
    List<@Valid VariantRequest> variants;
}
