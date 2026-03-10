package com.example.nhom3.project.modules.feedback.service;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse createFeedback(Long userId, FeedbackRequest request);
    List<FeedbackResponse> getProductFeedbacks(Long productId);
    List<FeedbackResponse> getCourtFeedbacks(Long courtId);
}