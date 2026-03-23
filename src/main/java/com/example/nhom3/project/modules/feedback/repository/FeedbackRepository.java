package com.example.nhom3.project.modules.feedback.repository;

import com.example.nhom3.project.modules.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findByProductId(UUID productId);
    List<Feedback> findByCourtId(UUID courtId);
    List<Feedback> findByUserId(UUID userId);
    List<Feedback> findByStatus(String status);
}