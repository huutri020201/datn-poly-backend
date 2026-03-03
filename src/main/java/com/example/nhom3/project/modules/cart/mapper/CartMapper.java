package com.example.nhom3.project.modules.cart.mapper;

import com.example.nhom3.project.modules.cart.dto.response.CartItemResponse;
import com.example.nhom3.project.modules.cart.dto.response.CartResponse;
import com.example.nhom3.project.modules.cart.entity.Cart;
import com.example.nhom3.project.modules.cart.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public CartResponse toResponse(Cart cart) {

        if (cart == null) {
            return null;
        }

        List<CartItemResponse> itemResponses =
                cart.getItems() == null
                        ? Collections.emptyList()
                        : cart.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList();

        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .status(cart.getStatus() != null ? cart.getStatus().name() : null)
                .items(itemResponses)
                .totalAmount(totalAmount)
                .totalItems(totalItems)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public CartItemResponse toItemResponse(CartItem item) {

        if (item == null) {
            return null;
        }

        BigDecimal totalPrice = calculateTotalPrice(
                item.getUnitPrice(),
                item.getQuantity()
        );

        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .variantId(item.getVariantId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(totalPrice)
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, Integer quantity) {

        if (unitPrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}