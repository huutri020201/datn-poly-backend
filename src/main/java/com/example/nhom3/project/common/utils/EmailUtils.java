package com.example.nhom3.project.common.utils;

import com.example.nhom3.project.modules.booking.dto.response.BookingResponseDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor // Annotation này của Lombok sẽ tự động Inject (tiêm) JavaMailSender vào
public class EmailUtils {

    // 1. KHAI BÁO JAVAMAILSENDER Ở ĐÂY
    private final JavaMailSender javaMailSender;

    // 2. Hàm gửi mail đặt sân
    @Async
    public void sendBookingConfirmationEmail(String toEmail, String pitchName, BookingResponseDTO booking) {
        try {
            // Sử dụng javaMailSender để tạo form mail
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Xác nhận đặt sân thành công - " + pitchName);

            String htmlMsg = "<h3>Chào bạn,</h3>"
                    + "<p>Bạn đã đặt sân thành công...</p>";

            helper.setText(htmlMsg, true);

            // Sử dụng hàm send() của javaMailSender để chính thức gửi đi
            javaMailSender.send(message);

        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}