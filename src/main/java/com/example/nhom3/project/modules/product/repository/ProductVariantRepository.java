package com.example.nhom3.project.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nhom3.project.modules.product.entity.ProductVariant;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {
}