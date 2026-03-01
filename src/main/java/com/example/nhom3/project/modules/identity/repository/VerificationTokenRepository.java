package com.example.nhom3.project.modules.identity.repository;


import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByExpiryAtBefore(LocalDateTime now);

    void deleteByUser(User user); // Để dọn sạch nếu user đăng ký lại
}