package com.example.nhom3.project.modules.inventory.service.Impl;

import com.example.nhom3.project.modules.inventory.entity.Inventory;
import com.example.nhom3.project.modules.inventory.entity.InventoryLog;
import com.example.nhom3.project.modules.inventory.repository.InventoryLogRepository;
import com.example.nhom3.project.modules.inventory.repository.InventoryRepository;
import com.example.nhom3.project.modules.inventory.service.InventoryService;
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
    InventoryRepository inventoryRepository;
    InventoryLogRepository logRepository;

    @Override
    public void initializeStock(UUID variantId, int initialQty) {
        Inventory inventory = Inventory.builder()
                .productVariantId(variantId)
                .totalQty(initialQty)
                .availableQty(initialQty)
                .reservedQty(0)
                .build();

        inventory = inventoryRepository.save(inventory);

        logRepository.save(InventoryLog.builder()
                .inventory(inventory)
                .changeQty(initialQty)
                .actionType("INBOUND")
                .note("Lượng hàng tồn kho ban đầu từ khi tạo ra sản phẩm.")
                .build());
    }

    @Override
    public void reserveStock(UUID variantId, int quantity, UUID orderId) {
        Inventory inventory = inventoryRepository.findByProductVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("INVENTORY_NOT_FOUND"));

        if (inventory.getAvailableQty() < quantity) {
            throw new RuntimeException("OUT_OF_STOCK");
        }

        // Cập nhật con số
        inventory.setAvailableQty(inventory.getAvailableQty() - quantity);
        inventory.setReservedQty(inventory.getReservedQty() + quantity);

        inventoryRepository.save(inventory);

        logRepository.save(InventoryLog.builder()
                .inventory(inventory)
                .changeQty(-quantity)
                .actionType("RESERVE")
                .referenceId(orderId)
                .build());
    }

    @Override
    public void confirmOutbound(UUID variantId, int quantity, UUID orderId) {
        Inventory inventory = inventoryRepository.findByProductVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("INVENTORY_NOT_FOUND"));
        if (inventory.getReservedQty() < quantity) {
            throw new RuntimeException("INVALID_RESERVE_QUANTITY");
        }

        inventory.setTotalQty(inventory.getTotalQty() - quantity);
        inventory.setReservedQty(inventory.getReservedQty() - quantity);

        inventoryRepository.save(inventory);

        logRepository.save(InventoryLog.builder()
                .inventory(inventory)
                .changeQty(-quantity)
                .actionType("OUTBOUND")
                .referenceId(orderId)
                .note("Xuất kho thực tế sau khi thanh toán đơn hàng")
                .build());
    }

    @Override
    public void cancelReservation(UUID variantId, int quantity, UUID orderId) {
        Inventory inventory = inventoryRepository.findByProductVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("INVENTORY_NOT_FOUND"));
        if (inventory.getReservedQty() < quantity) {
            throw new RuntimeException("INVALID_CANCEL_QUANTITY");
        }

        inventory.setAvailableQty(inventory.getAvailableQty() + quantity);
        inventory.setReservedQty(inventory.getReservedQty() - quantity);
        inventoryRepository.save(inventory);

        logRepository.save(InventoryLog.builder()
                .inventory(inventory)
                .changeQty(quantity)
                .actionType("CANCEL_RESERVE")
                .referenceId(orderId)
                .note("Hoàn lại kho sau khi hủy đơn hàng")
                .build());
    }
}