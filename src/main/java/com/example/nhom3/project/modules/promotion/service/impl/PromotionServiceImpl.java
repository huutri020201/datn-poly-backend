package com.example.nhom3.project.modules.promotion.service.impl;

import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.promotion.entity.UserVoucher;
import com.example.nhom3.project.modules.promotion.entity.Voucher;

import com.example.nhom3.project.modules.promotion.repo.UserVoucherRepository;
import com.example.nhom3.project.modules.promotion.repo.VoucherRepository;
import com.example.nhom3.project.modules.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final VoucherRepository voucherRepository;
    private final UserVoucherRepository userVoucherRepository;

    @Override
    @Transactional
    public void rewardWelcomeVoucher(User user) {
        // 1. Tìm xem hệ thống có chiến dịch WELCOME nào đang chạy không
        Optional<Voucher> welcomeVoucherOpt = voucherRepository.findActiveVoucherByEventType("WELCOME");

        if (welcomeVoucherOpt.isEmpty()) {
            return;
        }

        Voucher voucher = welcomeVoucherOpt.get();

        // 2. Check xem User này đã nhận mã này chưa
        if (userVoucherRepository.existsByUserIdAndVoucherId(user.getId(), voucher.getId())) {
            return; 
        }

        // 3. Check xem Voucher này có bị giới hạn số lượng không
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            return; // Đã phát hết mã
        }

        // 4. Tạo UserVoucher
        UserVoucher userVoucher = UserVoucher.builder()
                .user(user)
                .voucher(voucher)
                .status("AVAILABLE")
                .build();
        userVoucherRepository.save(userVoucher);

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);
        
        System.out.println("Đã tặng thành công Voucher Khách Hàng: " + user.getEmail());
    }
    @Override
    @Transactional
    public void rewardVipVoucher(User user, BigDecimal orderTotalAmount) {
        Optional<Voucher> rewardVoucherOpt = voucherRepository.findActiveVoucherByEventType("REWARD");

        if (rewardVoucherOpt.isEmpty()) {
            return;
        }

        Voucher voucher = rewardVoucherOpt.get();

        // SO SÁNH: Tiền hóa đơn có đạt mức tối thiểu của Voucher yêu cầu không?
        if (orderTotalAmount.compareTo(voucher.getMinOrderValue()) < 0) {
            return;
        }

        // Kiểm tra xem user đã nhận mã này chưa
        if (userVoucherRepository.existsByUserIdAndVoucherId(user.getId(), voucher.getId())) {
            return;
        }

        // Kiểm tra giới hạn số lượng phát mã
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            return;
        }

        //  Đủ điều kiện
        UserVoucher userVoucher = UserVoucher.builder()
                .user(user)
                .voucher(voucher)
                .status("AVAILABLE")
                .build();
        userVoucherRepository.save(userVoucher);

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);

        System.out.println("🎉 Đã tặng Voucher VIP cho Khách Hàng " + user.getEmail() + " vì có đơn hàng " + orderTotalAmount + " VNĐ!");

    }
    @Override
    @Transactional
    public void claimVoucher(User user, String voucherCode) {
        // 1. Tìm xem mã này có tồn tại không
        Voucher voucher = voucherRepository.findByCode(voucherCode)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại!"));

        // 2. Kiểm tra xem mã còn hiệu lực không
        LocalDateTime now = LocalDateTime.now();
        if (!voucher.getIsActive() || now.isBefore(voucher.getStartDate()) || now.isAfter(voucher.getEndDate())) {
            throw new RuntimeException("Mã giảm giá này đã hết hạn hoặc chưa tới thời gian sử dụng!");
        }

        // 3. Kiểm tra số lượng
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new RuntimeException("Rất tiếc! Mã giảm giá này đã được săn hết.");
        }

        // 4. Kiểm tra xem user này đã lưu mã này vào ví chưa
        if (userVoucherRepository.existsByUserIdAndVoucherId(user.getId(), voucher.getId())) {
            throw new RuntimeException("Bạn đã lưu mã này vào ví rồi, không thể lấy thêm!");
        }

        // Lưu vào ví cho User
        UserVoucher userVoucher = UserVoucher.builder()
                .user(user)
                .voucher(voucher)
                .status("AVAILABLE")
                .build();
        userVoucherRepository.save(userVoucher);

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);

        System.out.println("Khách hàngpackage com.example.nhom3.project.modules.promotion.controller;\n" +
                "\n" +
                "import com.example.nhom3.project.modules.identity.entity.User;\n" +
                "import com.example.nhom3.project.modules.identity.repository.UserRepository;\n" +
                "import com.example.nhom3.project.modules.promotion.service.PromotionService;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "import java.util.UUID;\n" +
                "\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/v1/promotions\")\n" +
                "@RequiredArgsConstructor\n" +
                "public class PromotionController {\n" +
                "\n" +
                "    private final PromotionService promotionService;\n" +
                "    private final UserRepository userRepository;\n" +
                "\n" +
                "    // API Săn mã (Client truyền userId và mã code lên)\n" +
                "    @PostMapping(\"/claim\")\n" +
                "    public ResponseEntity<?> claimVoucher(@RequestBody Map<String, String> request) {\n" +
                "        try {\n" +
                "            UUID userId = UUID.fromString(request.get(\"userId\"));\n" +
                "            String voucherCode = request.get(\"voucherCode\");\n" +
                "\n" +
                "            User user = userRepository.findById(userId)\n" +
                "                    .orElseThrow(() -> new RuntimeException(\"Không tìm thấy User!\"));\n" +
                "\n" +
                "            promotionService.claimVoucher(user, voucherCode);\n" +
                "\n" +
                "            return ResponseEntity.ok(Map.of(\"message\", \"Lưu mã \" + voucherCode + \" thành công!\"));\n" +
                "            \n" +
                "        } catch (Exception e) {\n" +
                "            return ResponseEntity.badRequest().body(Map.of(\"error\", e.getMessage()));\n" +
                "        }\n" +
                "    }\n" +
                "} " + user.getEmail() + " đã săn thành công mã " + voucherCode);
    }
}