package com.example.nhom3.project.modules.payment.service;

import com.example.nhom3.project.modules.order.entity.Order;

import java.util.Map;

public interface PaymentService {
    void savePaymentHistory(Order order, Map<String, String> params);
}
