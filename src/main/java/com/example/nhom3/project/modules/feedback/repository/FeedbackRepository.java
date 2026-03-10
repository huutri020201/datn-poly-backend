package com.example.nhom3.project.modules.feedback.repository;

import com.example.nhom3.project.modules.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductId(Long productId);
    List<Feedback> findByCourtId(Long courtId);
    List<Feedback> findByUserId(Long userId);
}
