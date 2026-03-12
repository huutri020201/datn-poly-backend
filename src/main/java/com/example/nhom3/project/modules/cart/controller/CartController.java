package com.example.nhom3.project.modules.cart.controller;

import com.example.nhom3.project.modules.cart.dto.request.AddToCartRequest;
import com.example.nhom3.project.modules.cart.dto.request.UpdateCartItemRequest;
import com.example.nhom3.project.modules.cart.dto.response.CartResponse;
import com.example.nhom3.project.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getCurrentUserCart(@AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        return cartService.getCurrentUserCart(userId);
    }

    @PostMapping("/add")
    public CartResponse addToCart(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AddToCartRequest request
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        return cartService.addToCart(userId, request);
    }

    @PutMapping("/item")
    public CartResponse updateItem(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UpdateCartItemRequest request
    ) {

        UUID userId = UUID.fromString(jwt.getSubject());

        return cartService.updateItem(userId, request);
    }

    @DeleteMapping("/item/{variantId}")
    public void removeItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID variantId
    ) {

        UUID userId = UUID.fromString(jwt.getSubject());

        cartService.removeItem(userId, variantId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        cartService.clearCart(userId);
    }
}