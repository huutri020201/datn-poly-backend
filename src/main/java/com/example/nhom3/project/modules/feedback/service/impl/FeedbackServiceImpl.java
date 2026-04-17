package com.example.nhom3.project.modules.feedback.service.impl;

import com.example.nhom3.project.common.exception.AppException;
import com.example.nhom3.project.common.exception.ErrorCode;
import com.example.nhom3.project.common.utils.SecurityUtils;
import com.example.nhom3.project.modules.feedback.dto.FeedbackRequest;
import com.example.nhom3.project.modules.feedback.dto.FeedbackResponse;
import com.example.nhom3.project.modules.feedback.entity.Feedback;
import com.example.nhom3.project.modules.feedback.repository.FeedbackRepository;
import com.example.nhom3.project.modules.feedback.service.FeedbackService;
import com.example.nhom3.project.modules.identity.entity.Profile;
import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.repository.ProfileRepository;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
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
                .updatedAt(LocalDateTime.now())
                .build();

        feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getProductFeedbacks(UUID productId) {
        return feedbackRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getCourtFeedbacks(UUID courtId) {
        return feedbackRepository.findByCourtId(courtId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getUserFeedbacks(UUID userId) {
        return feedbackRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public FeedbackResponse updateFeedback(UUID userId, UUID feedbackId, FeedbackRequest request) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        if (!feedback.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot edit this feedback");
        }

        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        feedback.setImageUrls(request.getImageUrls());
        feedback.setUpdatedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

    @Override
    @Transactional
    public void deleteFeedback(UUID userId, UUID feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        if (!feedback.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete this feedback");
        }

        feedbackRepository.delete(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public FeedbackResponse updateStatus(UUID feedbackId, String status) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setStatus(status);
        feedback.setUpdatedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

    private FeedbackResponse mapToResponse(Feedback feedback) {
        UUID userId = feedback.getUserId();
        String phone = userRepository.findById(userId)
                .map(User::getPhone)
                .orElse("");
        String name = profileRepository.findById(userId)
                .map(Profile::getFullName)
                .orElse("Khách hàng");

        return FeedbackResponse.builder()
                .id(feedback.getId())
                .userId(userId)
                .productId(feedback.getProductId())
                .orderId(feedback.getOrderId())
                .userName(name)
                .phoneNumber(phone)
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .imageUrls(feedback.getImageUrls())
                .createdAt(feedback.getCreatedAt())
                .status(feedback.getStatus())
                .adminReply(feedback.getAdminReply())
                .build();
    }

    @Override
    @Transactional
    public FeedbackResponse replyToFeedback(UUID feedbackId, String reply) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setAdminReply(reply);
        feedback.setUpdatedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }
}