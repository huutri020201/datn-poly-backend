package com.example.nhom3.project.modules.promotion.service;

import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.promotion.entity.UserVoucher;
import com.example.nhom3.project.modules.promotion.entity.Voucher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PromotionService {
    void rewardWelcomeVoucher(User user); // Tặng mã cho user mới
    void rewardVipVoucher(User user, BigDecimal orderTotalAmount);
    void claimVoucher(User user, String voucherCode);
    List<Voucher> getMyVouchers(UUID userId);
}