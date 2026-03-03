package com.example.nhom3.project.modules.cart.service;

import com.example.nhom3.project.modules.cart.dto.request.AddToCartRequest;
import com.example.nhom3.project.modules.cart.dto.response.CartResponse;

import java.util.UUID;

public interface CartService {
    CartResponse getCurrentUserCart(UUID userId);
    CartResponse addToCart(UUID userId, AddToCartRequest request);
    void removeItem(UUID userId, UUID variantId);
    void clearCart(UUID userId);
}