package com.example.nhom3.project.modules.payment.entity;

import com.example.nhom3.project.modules.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_method")
    private String paymentMethod; // VNPAY, MOMO

    @Column(name = "transaction_id")
    private String transactionId; // Mã giao dịch của VNPAY/MoMo

    @Column(name = "bank_code")
    private String bankCode;

    private BigDecimal amount;

    private String status; // SUCCESS, FAILED

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}