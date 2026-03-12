package com.example.nhom3.project.modules.product.entity;

import com.example.nhom3.project.common.utils.SlugHelper;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @Column(nullable = false, length = 255)
    String name;

    @Column(unique = true, nullable = false)
    String slug;

    @Column(name = "base_price", nullable = false)
    BigDecimal basePrice;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(name = "created_at", insertable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;

    @PrePersist
    @PreUpdate
    public void ensureSlug() {
        // Với Product, bạn có thể nối thêm 1 đoạn ID ngắn để tránh trùng lặp tuyệt đối
        // vì SQL của bạn yêu cầu UNIQUE cho slug.
        String baseSlug = SlugHelper.generate(this.name);
        this.slug = baseSlug;
    }
}