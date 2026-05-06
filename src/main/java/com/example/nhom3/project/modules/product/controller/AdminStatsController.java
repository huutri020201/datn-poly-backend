package com.example.nhom3.project.modules.product.controller;

import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.product.dto.response.DashboardResponse;
import com.example.nhom3.project.modules.product.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {
    private final StatsService statsService;
    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> getDashboardStats() {
        return ApiResponse.success(
                statsService.getDashboardData(),
                "Lấy dữ liệu thống kê thành công"
        );
    }
}