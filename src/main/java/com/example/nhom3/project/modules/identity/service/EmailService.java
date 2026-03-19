package com.example.nhom3.project.modules.identity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pthao101023@gmail.com"); // Email bạn đã cấu hình trong yml
        message.setTo(to);
        message.setSubject("MÃ XÁC THỰC ĐẶT LẠI MẬT KHẨU");
        message.setText("Chào bạn,\n\nMã OTP để hoàn tất quá trình đặt lại mật khẩu của bạn là: " + otpCode +
                ".\n\nMã này có hiệu lực trong 2 phút. Vui lòng không cung cấp mã này cho bất kỳ ai.");
        mailSender.send(message);
    }
}