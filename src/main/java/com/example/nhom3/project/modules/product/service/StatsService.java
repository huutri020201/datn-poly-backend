package com.example.nhom3.project.modules.product.service;

import com.example.nhom3.project.modules.booking.repository.BookingRepository;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import com.example.nhom3.project.modules.order.repository.OrderRepository;
import com.example.nhom3.project.modules.product.dto.response.DashboardResponse;
import com.example.nhom3.project.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BookingRepository bookingRepository;

    public DashboardResponse getDashboardData() {
        // 1. Lấy các con số tổng quát
        long totalUsers = userRepository.count();
        long totalProducts = productRepository.count();
        long totalBookings = bookingRepository.count();
        // Lấy doanh thu
        BigDecimal totalRevenue = orderRepository.calculateTotalRevenue();

        // Xử lý doanh thu tháng
        List<Object[]> monthlyRaw = orderRepository.getMonthlyRevenueNative();
        List<DashboardResponse.MonthlyRevenue> monthlyStats = monthlyRaw.stream()
                .map(obj -> new DashboardResponse.MonthlyRevenue(
                        "Tháng " + Math.round(Double.parseDouble(obj[0].toString())),
                        new BigDecimal(obj[1].toString())
                ))
                .toList();

        // Xử lý Top sản phẩm (Sửa lại index obj[0] là name, obj[1] là sold)
        List<Object[]> topProdRaw = orderRepository.getTopSellingProductsNative();
        List<DashboardResponse.TopProduct> topProducts = topProdRaw.stream()
                .map(obj -> new DashboardResponse.TopProduct(
                        obj[0].toString(), // product_name
                        Long.parseLong(obj[1].toString()) // totalSold
                ))
                .toList();

        return DashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalProducts(totalProducts)
                .totalBookings(totalBookings)
                .totalRevenue(totalRevenue)
                .monthlyRevenue(monthlyStats)
                .topProducts(topProducts)
                .build();
    }
}