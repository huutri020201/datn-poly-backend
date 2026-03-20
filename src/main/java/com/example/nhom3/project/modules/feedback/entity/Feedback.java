package com.example.nhom3.project.modules.feedback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private UUID userId;
    private UUID productId;
    private UUID courtId;
    private UUID orderId;
    private UUID bookingId;
    private Integer rating;
    @Column(columnDefinition = "TEXT")
    private String comment;
    private String imageUrls;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}