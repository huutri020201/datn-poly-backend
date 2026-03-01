package com.example.nhom3.project.modules.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "product_variants")
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @Column(unique = true, nullable = false)
    String sku;

    @Column(name = "stock_qty")
    int stockQty;

    @JdbcTypeCode(SqlTypes.JSON) // Hibernate 6 mapping cho JSONB
    @Column(columnDefinition = "jsonb")
    Map<String, Object> attributes;

    @Column(name = "price_override")
    BigDecimal priceOverride;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "is_active")
    boolean isActive;
}