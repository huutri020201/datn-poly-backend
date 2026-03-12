package com.example.nhom3.project.modules.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "product_variant_id", nullable = false, unique = true)
    private UUID productVariantId;

    @Column(name = "total_qty", nullable = false)
    private Integer totalQty;

    @Column(name = "available_qty", nullable = false)
    private Integer availableQty;

    @Column(name = "reserved_qty", nullable = false)
    private Integer reservedQty;

    @Version
    private Integer version;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;
}