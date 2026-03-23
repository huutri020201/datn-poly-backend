package com.example.nhom3.project.modules.feedback.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FeedbackRequest {
    private UUID productId;
    private UUID courtId;
    private UUID orderId;
    private UUID bookingId;
    private Integer rating;
    private String comment;
    private String imageUrls;
}