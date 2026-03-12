package com.example.nhom3.project.modules.inventory.repository;

import com.example.nhom3.project.modules.inventory.entity.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, UUID> {
}
