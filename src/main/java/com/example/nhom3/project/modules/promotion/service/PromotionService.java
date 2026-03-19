package com.example.nhom3.project.modules.promotion.service;

import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.promotion.entity.Voucher;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PromotionService {
    void rewardWelcomeVoucher(User user); // Tặng mã cho user mới
    void rewardVipVoucher(User user, BigDecimal orderTotalAmount);
    void claimVoucher(User user, String voucherCode);
    List<Voucher> getMyVouchers(UUID userId);
    BigDecimal validateAndCalculateDiscount(UUID userId, String voucherCode, BigDecimal orderSubTotal);
    void useVoucher(UUID userId, String voucherCode, UUID orderId);
    void refundVoucher(UUID userId, String voucherCode);
}