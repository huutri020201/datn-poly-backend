package com.example.nhom3.project.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nhom3.project.modules.product.entity.Inventory;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {
}