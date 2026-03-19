package com.example.nhom3.project.modules.identity.service.listener;

import com.example.nhom3.project.modules.identity.dto.event.EmailOtpEvent;
import com.example.nhom3.project.modules.identity.dto.event.SmsOtpEvent;
import com.example.nhom3.project.modules.identity.service.EmailService;
import com.example.nhom3.project.modules.identity.service.TelegramSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpEventListener {

    private final EmailService emailService;
    private final TelegramSmsService telegramSmsService;

    @Async
    @EventListener
    public void handleEmailOtpEvent(EmailOtpEvent event) {
        log.info("Đang xử lý gửi Email OTP tới: {}", event.getEmail());
        try {
            emailService.sendOtpEmail(event.getEmail(), event.getOtpCode());
            log.info("Gửi Email OTP thành công!");
        } catch (Exception e) {
            log.error("Lỗi khi gửi Email OTP: {}", e.getMessage());
        }
    }

    @Async
    @EventListener
    public void handleSmsOtpEvent(SmsOtpEvent event) {
        log.info("Đang gửi OTP tới: {}", event.getPhone());
        try {
            telegramSmsService.sendOtp(event.getPhone(), event.getOtpCode());
            log.info("Gửi OTP thành công!");
        } catch (Exception e) {
            log.error("Lỗi khi gửi OTP: {}", e.getMessage());
        }
    }
}