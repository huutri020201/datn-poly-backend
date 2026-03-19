package com.example.nhom3.project.modules.order.dto.request;

import com.example.nhom3.project.modules.order.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    OrderStatus status;
}
