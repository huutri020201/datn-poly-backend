package com.example.nhom3.project.modules.promotion.repo;

import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.promotion.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    
    // Tìm Voucher theo loại sự kiện
    @Query("SELECT v FROM Voucher v WHERE v.eventType = :eventType AND v.isActive = true " +
           "AND CURRENT_TIMESTAMP BETWEEN v.startDate AND v.endDate")
    Optional<Voucher> findActiveVoucherByEventType(@Param("eventType") String eventType);
    Optional<Voucher> findByCode(String code);


}