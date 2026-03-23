package com.example.nhom3.project.modules.feedback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class FeedbackResponse {
    private UUID id;
    private UUID userId;
    private UUID productId;
    private UUID orderId;
    private String userName;
    private String phoneNumber;
    private Integer rating;
    private String comment;
    private String imageUrls;
    private LocalDateTime createdAt;
}