package com.example.nhom3.project.modules.order.service;

import com.example.nhom3.project.modules.order.dto.request.OrderPlaceRequest;
import com.example.nhom3.project.modules.order.dto.response.OrderResponse;
import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.order.entity.OrderStatus;
import com.example.nhom3.project.modules.product.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    // Cho Client
    OrderResponse placeOrder(UUID userId, OrderPlaceRequest request);
    List<OrderResponse> getMyOrders(UUID userId);
    OrderResponse getOrderDetail(UUID orderId);

    // Cho Admin
    PageResponse<OrderResponse> getAllOrders(int page, int size, String status);
    OrderResponse updateStatus(UUID orderId, OrderStatus status);

    Order getEntityById(UUID orderId);
    void completePayment(UUID orderId);
    void failPayment(UUID orderId);
}
