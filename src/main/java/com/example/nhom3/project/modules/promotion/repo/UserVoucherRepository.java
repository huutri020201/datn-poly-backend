package com.example.nhom3.project.modules.promotion.repo;

import com.example.nhom3.project.modules.promotion.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, UUID> {
    
    // Kiểm tra xem user đã sở hữu voucher này chưa
    boolean existsByUserIdAndVoucherId(UUID userId, UUID voucherId);
    @Query("SELECT uv FROM UserVoucher uv JOIN FETCH uv.voucher WHERE uv.user.id = :userId AND uv.status = :status")
    List<UserVoucher> findAllByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") String status);
    Optional<UserVoucher> findByUserIdAndVoucherId(UUID userId, UUID voucherId);
}