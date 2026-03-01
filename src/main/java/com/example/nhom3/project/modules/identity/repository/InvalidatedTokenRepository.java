package com.example.nhom3.project.modules.identity.repository;

import com.example.nhom3.project.modules.identity.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    @Modifying
    @Transactional
    void deleteByExpiryAtBefore(Instant now);
}
