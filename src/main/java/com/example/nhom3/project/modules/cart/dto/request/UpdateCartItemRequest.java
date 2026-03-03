package com.example.nhom3.project.modules.cart.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartItemRequest {
    private UUID variantId;
    private Integer quantity;
}