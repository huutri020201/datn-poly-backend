package com.example.nhom3.project.modules.promotion.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(unique = true, nullable = false, length = 50)
    String code;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(name = "discount_type", nullable = false, length = 50)
    String discountType;

    @Column(name = "discount_value", nullable = false)
    BigDecimal discountValue;

    @Column(name = "max_discount_amount")
    BigDecimal maxDiscountAmount;

    @Column(name = "min_order_value")
    BigDecimal minOrderValue;

    @Column(name = "event_type", nullable = false, length = 50)
    String eventType;

    @Column(name = "applicable_to", length = 50)
    String applicableTo;

    @Column(name = "start_date", nullable = false)
    LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    LocalDateTime endDate;

    @Column(name = "usage_limit")
    Integer usageLimit;

    @Column(name = "used_count")
    Integer usedCount;

    @Column(name = "is_active")
    Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    // Tự động gán giá trị mặc định trước khi lưu vào Database lần đầu tiên
    @PrePersist
    protected void onCreate() {
        if (this.usedCount == null) this.usedCount = 0;
        if (this.isActive == null) this.isActive = true;
        if (this.applicableTo == null) this.applicableTo = "ALL";
        if (this.minOrderValue == null) this.minOrderValue = BigDecimal.ZERO;
    }
}