package com.example.nhom3.project.modules.product.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    long totalUsers;
    long totalProducts;
    long totalBookings;
    BigDecimal totalRevenue;
    List<MonthlyRevenue> monthlyRevenue;
    List<TopProduct> topProducts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthlyRevenue {
        String month;
        BigDecimal amount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopProduct {
        String productName;
        long totalSold;
    }
}
