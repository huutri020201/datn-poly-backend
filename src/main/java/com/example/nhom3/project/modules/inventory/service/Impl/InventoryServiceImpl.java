package com.example.nhom3.project.modules.inventory.service.Impl;

import com.example.nhom3.project.modules.inventory.service.InventoryService;
import com.example.nhom3.project.modules.product.entity.ProductVariant;
import com.example.nhom3.project.modules.product.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class InventoryServiceImpl implements InventoryService {

    ProductVariantRepository variantRepository;

    @Override
    public void initializeStock(UUID variantId, int initialQty) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("VARIANT_NOT_FOUND"));
        variant.setStockQty(initialQty);
        variantRepository.save(variant);
        log.info("Đã khởi tạo kho cho variant {}: {}", variantId, initialQty);
    }

    @Override
    public void deductStock(UUID variantId, int quantity) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("VARIANT_NOT_FOUND"));
        int currentStock = variant.getStockQty();
        if (currentStock < quantity) {
            throw new RuntimeException("OUT_OF_STOCK: " + variantId);
        }
        variant.setStockQty(currentStock - quantity);
        variantRepository.save(variant);

        log.info("Đã trừ {} sản phẩm từ kho của variant {}", quantity, variantId);
    }

    @Override
    public void addStock(UUID variantId, int quantity) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("VARIANT_NOT_FOUND"));
        int currentStock = variant.getStockQty();
        variant.setStockQty(currentStock + quantity);
        variantRepository.save(variant);

        log.info("Đã hoàn lại {} sản phẩm vào kho của variant {}", quantity, variantId);
    }

    @Override
    public void reserveStock(UUID variantId, int quantity, UUID orderId) {
        deductStock(variantId, quantity);
    }

    @Override
    public void confirmOutbound(UUID variantId, int quantity, UUID orderId) {
    }

    @Override
    public void cancelReservation(UUID variantId, int quantity, UUID orderId) {
        addStock(variantId, quantity);
    }
}