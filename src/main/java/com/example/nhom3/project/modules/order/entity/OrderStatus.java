package com.example.nhom3.project.modules.order.entity;

public enum OrderStatus {
    PENDING,    // Chờ xác nhận/thanh toán
    CONFIRMED,  // Đã xác nhận, bắt đầu đóng gói
    SHIPPING,   // Đang giao hàng
    DELIVERED, // Giao hàng thành công
    SUCCESS, // Mua tại cửa hàng
    CANCELLED   // Đã hủy
}
