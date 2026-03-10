package com.example.nhom3.project.modules.promotion.controller;

import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import com.example.nhom3.project.modules.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final UserRepository userRepository;

    @PostMapping("/claim")
    public ResponseEntity<?> claimVoucher(@RequestBody Map<String, String> request) {
        try {
            UUID userId = UUID.fromString(request.get("userId"));
            String voucherCode = request.get("voucherCode");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy User!"));

            promotionService.claimVoucher(user, voucherCode);

            return ResponseEntity.ok(Map.of("message", "Lưu mã " + voucherCode + " thành công!"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}