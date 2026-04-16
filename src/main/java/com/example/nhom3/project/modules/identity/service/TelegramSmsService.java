package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.config.TelegramProperties;
import com.example.nhom3.project.modules.identity.dto.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramSmsService {

    TelegramProperties telegramProperties;
    RestTemplate restTemplate;

    public void sendNotification(NotificationEvent event) {
        String header = switch (event.getType()) {
            case REGISTER -> "🔑 [XÁC THỰC ĐĂNG KÝ]";
            case SECURITY_UPDATE -> "⚠️ [CẢNH BÁO BẢO MẬT]";
            case ACCOUNT_LOCK -> "🚫 [KHÓA TÀI KHOẢN]";
            default -> "🔔 [THÔNG BÁO]";
        };

        String body = switch (event.getType()) {
            case SECURITY_UPDATE -> String.format("Thông tin %s đã đổi thành: %s", event.getTargetName(), event.getNewValue());
            case ACCOUNT_LOCK -> "Lý do: " + event.getTargetName();
            default -> "Mã OTP của bạn là: " + event.getCode();
        };

        String text = header + "\n" + body;

        String url = "https://api.telegram.org/bot{token}/sendMessage?chat_id={chatId}&text={text}";

        Map<String, String> params = new HashMap<>();
        params.put("token", telegramProperties.getBotToken());
        params.put("chatId", telegramProperties.getChatId());
        params.put("text", text);

        try {
            restTemplate.getForObject(url, String.class, params);
            log.info("OTP Telegram gửi thành công tới: {}", event.getIdentifier());
        } catch (Exception e) {
            log.error("Lỗi API Telegram: {}", e.getMessage());
        }
    }
}
