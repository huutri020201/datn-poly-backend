package com.example.nhom3.project.modules.feedback.service.impl;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;
import com.example.nhom3.project.modules.feedback.entity.Feedback;
import com.example.nhom3.project.modules.feedback.repository.FeedbackRepository;
import com.example.nhom3.project.modules.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public FeedbackResponse createFeedback(Long userId, FeedbackRequest request) {

        Feedback feedback = Feedback.builder()
                .userId(userId)
                .productId(request.getProductId())
                .courtId(request.getCourtId())
                .orderId(request.getOrderId())
                .bookingId(request.getBookingId())
                .rating(request.getRating())
                .comment(request.getComment())
                .imageUrls(request.getImageUrls())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        feedbackRepository.save(feedback);

        return FeedbackResponse.builder()
                .id(feedback.getId())
                .userId(userId)
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .imageUrls(feedback.getImageUrls())
                .createdAt(feedback.getCreatedAt())
                .build();
    }

    @Override
    public List<FeedbackResponse> getProductFeedbacks(Long productId) {
        return feedbackRepository.findByProductId(productId)
                .stream()
                .map(f -> FeedbackResponse.builder()
                        .id(f.getId())
                        .userId(f.getUserId())
                        .rating(f.getRating())
                        .comment(f.getComment())
                        .imageUrls(f.getImageUrls())
                        .createdAt(f.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<FeedbackResponse> getCourtFeedbacks(Long courtId) {
        return feedbackRepository.findByCourtId(courtId)
                .stream()
                .map(f -> FeedbackResponse.builder()
                        .id(f.getId())
                        .userId(f.getUserId())
                        .rating(f.getRating())
                        .comment(f.getComment())
                        .imageUrls(f.getImageUrls())
                        .createdAt(f.getCreatedAt())
                        .build())
                .toList();
    }
}