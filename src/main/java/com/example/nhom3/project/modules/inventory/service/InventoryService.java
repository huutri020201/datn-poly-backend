package com.example.nhom3.project.modules.inventory.service;

import java.util.UUID;

public interface InventoryService {
    // Khởi tạo kho (Dùng khi Module Product tạo Variant mới)
    void initializeStock(UUID variantId, int initialQty);

    // Giữ chỗ hàng (Dùng khi khách đặt Order)
    void reserveStock(UUID variantId, int quantity, UUID orderId);

    // Hoàn tất xuất kho (Dùng khi thanh toán thành công)
    void confirmOutbound(UUID variantId, int quantity, UUID orderId);

    // Hủy giữ chỗ (Dùng khi hủy đơn hàng)
    void cancelReservation(UUID variantId, int quantity, UUID orderId);


}
