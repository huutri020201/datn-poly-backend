package com.example.nhom3.project.modules.feedback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FeedbackResponse {
    private Long id;
    private Long userId;
    private Integer rating;
    private String comment;
    private String imageUrls;
    private LocalDateTime createdAt;
}