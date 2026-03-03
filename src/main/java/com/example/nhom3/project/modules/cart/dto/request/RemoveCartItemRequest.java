package com.example.nhom3.project.modules.cart.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveCartItemRequest {
    private UUID variantId;
}
