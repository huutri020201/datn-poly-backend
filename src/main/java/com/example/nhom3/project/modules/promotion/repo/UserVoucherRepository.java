package com.example.nhom3.project.modules.promotion.repo;

import com.example.nhom3.project.modules.promotion.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, UUID> {
    
    // Kiểm tra xem user đã sở hữu voucher này chưa
    boolean existsByUserIdAndVoucherId(UUID userId, UUID voucherId);
}