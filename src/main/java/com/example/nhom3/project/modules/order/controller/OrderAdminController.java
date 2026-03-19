package com.example.nhom3.project.modules.order.controller;

import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.order.dto.request.OrderStatusUpdateRequest;
import com.example.nhom3.project.modules.order.dto.response.OrderResponse;
import com.example.nhom3.project.modules.order.service.OrderService;
import com.example.nhom3.project.modules.product.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Chặn tất cả user không phải Admin ngay tại lớp này
public class OrderAdminController {
    private final OrderService orderService;

    @GetMapping
    public ApiResponse<PageResponse<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        return ApiResponse.<PageResponse<OrderResponse>>builder()
                .result(orderService.getAllOrders(page, size, status))
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(
            @PathVariable UUID id,
            @RequestBody OrderStatusUpdateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateStatus(id, request.getStatus()))
                .build();
    }
}