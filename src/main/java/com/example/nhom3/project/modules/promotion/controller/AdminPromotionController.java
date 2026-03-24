package com.example.nhom3.project.modules.promotion.controller;

import com.example.nhom3.project.modules.promotion.entity.Voucher;
import com.example.nhom3.project.modules.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/promotions")
@RequiredArgsConstructor
public class AdminPromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        return ResponseEntity.ok(promotionService.getAllVouchers());
    }

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(promotionService.createVoucher(voucher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable UUID id, @RequestBody Voucher voucher) {
        return ResponseEntity.ok(promotionService.updateVoucher(id, voucher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable UUID id) {
        promotionService.deleteVoucher(id);
        return ResponseEntity.ok("Xóa thành công!");
    }
}