package com.example.nhom3.project.modules.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant_id", nullable = false)
    private UUID variantId;

    // Thông tin sản phẩm (Snapshot 100%)
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private String sku;

    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal priceAtPurchase;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "image_snapshot")
    private String imageSnapshot;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes_snapshot", columnDefinition = "jsonb")
    private Map<String, Object> attributesSnapshot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}