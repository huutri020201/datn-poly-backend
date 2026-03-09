package com.example.nhom3.project.modules.feedback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long id;
    private Long userId;
    private Long productId;
    private Long courtId;
    private Long orderId;
    private Long bookingId;
    private Integer rating;
    @Column(columnDefinition = "TEXT")
    private String comment;
    private String imageUrls;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}