package com.example.nhom3.project.modules.payment.service.impl;

import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.payment.entity.Payment;
import com.example.nhom3.project.modules.payment.repository.PaymentRepository;
import com.example.nhom3.project.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void savePaymentHistory(Order order, Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod("VNPAY")
                .transactionId(params.get("vnp_TransactionNo"))
                .bankCode(params.get("vnp_BankCode"))
                .amount(new BigDecimal(params.get("vnp_Amount")).divide(new BigDecimal(100)))
                .status("00".equals(responseCode) ? "SUCCESS" : "FAILED")
                .message(params.get("vnp_OrderInfo"))
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }
}
