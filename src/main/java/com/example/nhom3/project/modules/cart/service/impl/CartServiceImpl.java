package com.example.nhom3.project.modules.cart.service.impl;

import com.example.nhom3.project.modules.cart.dto.request.AddToCartRequest;
import com.example.nhom3.project.modules.cart.dto.request.UpdateCartItemRequest;
import com.example.nhom3.project.modules.cart.dto.response.CartResponse;
import com.example.nhom3.project.modules.cart.entity.Cart;
import com.example.nhom3.project.modules.cart.entity.CartItem;
import com.example.nhom3.project.modules.cart.entity.CartStatus;
import com.example.nhom3.project.modules.cart.mapper.CartMapper;
import com.example.nhom3.project.modules.cart.repository.CartItemRepository;
import com.example.nhom3.project.modules.cart.repository.CartRepository;
import com.example.nhom3.project.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse getCurrentUserCart(UUID userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userId));

        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse addToCart(UUID userId, AddToCartRequest request) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userId));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndVariantId(cart.getId(), request.getVariantId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setUpdatedAt(LocalDateTime.now());
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .productId(request.getProductId())
                    .variantId(request.getVariantId())
                    .quantity(request.getQuantity())
                    .unitPrice(request.getUnitPrice())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }

        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());

        return cartMapper.toResponse(cart);
    }

    @Override
    public void removeItem(UUID userId, UUID variantId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteByCartIdAndVariantId(cart.getId(), variantId);
    }

    @Override
    public void clearCart(UUID userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteByCartId(cart.getId());
    }

    private Cart createNewCart(UUID userId) {

        Cart cart = Cart.builder()
                .userId(userId)
                .status(CartStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return cartRepository.save(cart);
    }

    @Override
    public CartResponse checkout(UUID userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.setStatus(CartStatus.CHECKED_OUT);
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse updateItem(UUID userId, UpdateCartItemRequest request) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCartIdAndVariantId(cart.getId(), request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(request.getQuantity());
        item.setUpdatedAt(LocalDateTime.now());

        cartItemRepository.save(item);

        return cartMapper.toResponse(cart);
    }
}