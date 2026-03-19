package com.example.nhom3.project.modules.order.mapper;

import com.example.nhom3.project.modules.order.dto.response.OrderItemResponse;
import com.example.nhom3.project.modules.order.dto.response.OrderResponse;
import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items", source = "items")
    @Mapping(target = "paymentUrl", ignore = true)
    OrderResponse toResponse(Order order);

    OrderItemResponse toItemResponse(OrderItem item);
}
