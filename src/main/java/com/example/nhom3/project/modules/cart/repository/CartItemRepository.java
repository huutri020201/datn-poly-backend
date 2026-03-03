package com.example.nhom3.project.modules.cart.repository;

import com.example.nhom3.project.modules.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, UUID variantId);
    void deleteByCartIdAndVariantId(Long cartId, UUID variantId);
    void deleteByCartId(Long cartId);
}