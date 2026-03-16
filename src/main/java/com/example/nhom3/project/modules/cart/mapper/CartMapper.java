package com.example.nhom3.project.modules.cart.mapper;

import com.example.nhom3.project.modules.cart.dto.response.CartItemResponse;
import com.example.nhom3.project.modules.cart.dto.response.CartResponse;
import com.example.nhom3.project.modules.cart.entity.Cart;
import com.example.nhom3.project.modules.cart.entity.CartItem;

 import com.example.nhom3.project.modules.product.repository.ProductRepository;
 import com.example.nhom3.project.modules.product.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CartMapper {

     private final ProductRepository productRepository;
     private final ProductVariantRepository productVariantRepository;

    public CartResponse toResponse(Cart cart) {

        if (cart == null) {
            return null;
        }

        List<CartItemResponse> itemResponses =
                cart.getItems() == null
                        ? Collections.emptyList()
                        : cart.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList();

        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .status(cart.getStatus() != null ? cart.getStatus().name() : null)
                .items(itemResponses)
                .totalAmount(totalAmount)
                .totalItems(totalItems)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public CartItemResponse toItemResponse(CartItem item) {

        if (item == null) {
            return null;
        }

        BigDecimal totalPrice = calculateTotalPrice(
                item.getUnitPrice(),
                item.getQuantity()
        );

        String productNameStr = "Chưa xác định";
        String variantNameStr = "Mặc định";


        if (item.getProductId() != null) {
            productNameStr = productRepository.findById(item.getProductId())
                    .map(product -> product.getName())
                    .orElse("Sản phẩm không tồn tại");
        }

        if (item.getVariantId() != null) {
            variantNameStr = productVariantRepository.findById(item.getVariantId())
                    .map(v -> {
                        Map<String, Object> attrs = v.getAttributes();

                        if (attrs != null && !attrs.isEmpty()) {
                            Object colorObj = attrs.get("color");
                            Object sizeObj = attrs.get("size");

                            String color = colorObj != null ? colorObj.toString() : "";
                            String size = sizeObj != null ? sizeObj.toString() : "";

                            if (!color.isEmpty() && !size.isEmpty()) return color + " - " + size;
                            if (!color.isEmpty()) return color;
                            if (!size.isEmpty()) return size;
                        }

                        return v.getSku() != null ? v.getSku() : "Phân loại tiêu chuẩn";
                    })
                    .orElse("Phân loại không tồn tại");
        }


        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .variantId(item.getVariantId())
                .productName(productNameStr)
                .variantName(variantNameStr)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(totalPrice)
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, Integer quantity) {

        if (unitPrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}