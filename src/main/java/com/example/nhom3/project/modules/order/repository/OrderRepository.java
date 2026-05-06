package com.example.nhom3.project.modules.order.repository;

import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);

    // Query cho Admin lọc đơn hàng
    Page<Order> findAllByStatusOrderByCreatedAtDesc(OrderStatus status, Pageable pageable);

    // 1. Tính tổng doanh thu
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED'")
    BigDecimal calculateTotalRevenue();

    // 2. Lấy doanh thu theo từng tháng
    @Query(value = "SELECT EXTRACT(MONTH FROM created_at) as month, SUM(total_amount) as amount " +
            "FROM orders " +
            "WHERE EXTRACT(YEAR FROM created_at) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND status = 'DELIVERED' " +
            "GROUP BY month " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> getMonthlyRevenueNative();

    // 3. Lấy Top 5 sản phẩm bán chạy
    @Query(value = "SELECT product_name, SUM(quantity) as totalSold " +
            "FROM order_items " +
            "GROUP BY product_name " +
            "ORDER BY totalSold DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> getTopSellingProductsNative();
}