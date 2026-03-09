package com.example.nhom3.project.modules.feedback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {
    private Long productId;
    private Long courtId;
    private Long orderId;
    private Long bookingId;
    private Integer rating;
    private String comment;
    private String imageUrls;
}