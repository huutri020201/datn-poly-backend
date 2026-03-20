package com.example.nhom3.project.modules.feedback.service;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackResponse createFeedback(UUID userId, FeedbackRequest request);
    List<FeedbackResponse> getProductFeedbacks(UUID productId);
    List<FeedbackResponse> getCourtFeedbacks(UUID courtId);
    List<FeedbackResponse> getUserFeedbacks(UUID userId);
    FeedbackResponse updateFeedback(UUID userId, UUID feedbackId, FeedbackRequest request);
    void deleteFeedback(UUID userId, UUID feedbackId);
    List<FeedbackResponse> getAllFeedbacks();
    FeedbackResponse updateStatus(UUID feedbackId, String status);
}