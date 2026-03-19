package com.example.nhom3.project.modules.order.controller;

import com.example.nhom3.project.common.utils.SecurityUtils;
import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.order.dto.request.OrderPlaceRequest;
import com.example.nhom3.project.modules.order.dto.response.OrderResponse;
import com.example.nhom3.project.modules.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> placeOrder(
            HttpServletRequest request, // Thêm tham số này
            @RequestBody @Valid OrderPlaceRequest orderRequest) {

        UUID userId = SecurityUtils.getCurrentUserId();

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        return ApiResponse.<OrderResponse>builder()
                .result(orderService.placeOrder(userId, orderRequest))
                .build();
    }

    @GetMapping("/my-orders")
    public ApiResponse<List<OrderResponse>> getMyOrders() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getMyOrders(userId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderDetail(@PathVariable UUID id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderDetail(id))
                .build();
    }
}