package com.example.nhom3.project.modules.cart.repository;

import com.example.nhom3.project.modules.cart.entity.Cart;
import com.example.nhom3.project.modules.cart.entity.CartStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {"items"})
    Optional<Cart> findByUserIdAndStatus(UUID userId, CartStatus status);

}