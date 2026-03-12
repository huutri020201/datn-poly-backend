package com.example.nhom3.project.modules.product.repository;

import com.example.nhom3.project.modules.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, UUID> {
    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);
}