package com.example.nhom3.project.modules.product.repository;

import com.example.nhom3.project.modules.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    // Query tìm kiếm sản phẩm theo một thuộc tính trong JSON (Ví dụ: Tìm theo size)
    @Query(value = "SELECT * FROM product_variants WHERE attributes ->> :key = :value", nativeQuery = true)
    List<ProductVariant> findByAttribute(@Param("key") String key, @Param("value") String value);
    List<ProductVariant> findByProductId(UUID productId);
}
