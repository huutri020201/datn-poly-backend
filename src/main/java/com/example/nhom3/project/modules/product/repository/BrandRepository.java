package com.example.nhom3.project.modules.product.repository;

import com.example.nhom3.project.modules.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    boolean existsByName(String name);
    Optional<Brand> findBySlug(String slug);
}
