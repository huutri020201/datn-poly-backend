package com.example.nhom3.project.modules.feedback.controller;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;
import com.example.nhom3.project.modules.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public FeedbackResponse createFeedback(
            @RequestHeader("userId") Long userId,
            @RequestBody FeedbackRequest request) {

        return feedbackService.createFeedback(userId, request);
    }

    @GetMapping("/product/{productId}")
    public List<FeedbackResponse> getProductFeedbacks(@PathVariable Long productId) {
        return feedbackService.getProductFeedbacks(productId);
    }

    @GetMapping("/court/{courtId}")
    public List<FeedbackResponse> getCourtFeedbacks(@PathVariable Long courtId) {
        return feedbackService.getCourtFeedbacks(courtId);
    }

    @GetMapping("/my")
    public List<FeedbackResponse> getMyFeedbacks(
            @RequestHeader("userId") Long userId
    ) {
        return feedbackService.getUserFeedbacks(userId);
    }

    @PutMapping("/{feedbackId}")
    public FeedbackResponse updateFeedback(
            @RequestHeader("userId") Long userId,
            @PathVariable Long feedbackId,
            @RequestBody FeedbackRequest request
    ) {
        return feedbackService.updateFeedback(userId, feedbackId, request);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(
            @RequestHeader("userId") Long userId,
            @PathVariable Long feedbackId
    ) {
        feedbackService.deleteFeedback(userId, feedbackId);
    }
}
