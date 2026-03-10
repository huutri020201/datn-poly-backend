package com.example.nhom3.project.modules.promotion.service;

import com.example.nhom3.project.modules.identity.entity.User;

import java.math.BigDecimal;

public interface PromotionService {
    void rewardWelcomeVoucher(User user); // Tặng mã cho user mới
    void rewardVipVoucher(User user, BigDecimal orderTotalAmount);
    void claimVoucher(User user, String voucherCode);
}