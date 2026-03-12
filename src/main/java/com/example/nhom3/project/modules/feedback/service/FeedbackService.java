package com.example.nhom3.project.modules.feedback.service;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse createFeedback(Long userId, FeedbackRequest request);
    List<FeedbackResponse> getProductFeedbacks(Long productId);
    List<FeedbackResponse> getCourtFeedbacks(Long courtId);
    List<FeedbackResponse> getUserFeedbacks(Long userId);
    FeedbackResponse updateFeedback(Long userId, Long feedbackId, FeedbackRequest request);
    void deleteFeedback(Long userId, Long feedbackId);
    List<FeedbackResponse> getAllFeedbacks();
    FeedbackResponse updateStatus(Long feedbackId, String status);
}