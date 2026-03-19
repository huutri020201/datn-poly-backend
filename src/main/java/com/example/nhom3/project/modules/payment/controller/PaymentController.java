package com.example.nhom3.project.modules.payment.controller;

import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.order.service.OrderService;
import com.example.nhom3.project.modules.payment.service.PaymentService;
import com.example.nhom3.project.modules.payment.service.VNPAYService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final VNPAYService vnpayService;
    private final PaymentService paymentService;
    private final OrderService orderService; // Sẽ bổ sung hàm xử lý sau

    @GetMapping("/vnpay-callback")
    public ResponseEntity<?> handleCallback(@RequestParam Map<String, String> params) {
        // 1. Verify chữ ký (giữ nguyên)
        if (!vnpayService.verifySignature(params)) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://localhost:5173/payment/callback?status=error"))
                    .build();
        }

        UUID orderId = UUID.fromString(params.get("vnp_TxnRef"));
        Order order = orderService.getEntityById(orderId);
        paymentService.savePaymentHistory(order, params);

        // 2. Tự động build Query String một cách an toàn
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:5173/payment/callback");
        params.forEach(builder::queryParam);

        if ("00".equals(params.get("vnp_ResponseCode"))) {
            orderService.completePayment(orderId);
        } else {
            orderService.failPayment(orderId);
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(builder.build().toUri())
                .build();
    }
}