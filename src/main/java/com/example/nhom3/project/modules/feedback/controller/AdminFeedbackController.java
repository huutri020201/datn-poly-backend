package com.example.nhom3.project.modules.feedback.controller;

import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;
import com.example.nhom3.project.modules.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/admin/feedbacks")
@RequiredArgsConstructor
public class AdminFeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @PutMapping("/{id}/status")
    public FeedbackResponse updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return feedbackService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable UUID id) {
        feedbackService.deleteFeedback(null, id);
    }
}