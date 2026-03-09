package com.example.nhom3.project.modules.feedback.controller;

import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;
import com.example.nhom3.project.modules.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
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
}
