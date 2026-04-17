package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.config.TelegramProperties;
import com.example.nhom3.project.modules.identity.dto.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    @EventListener
    public void sendNotification(NotificationEvent event) {
        String identifier = event.getIdentifier();
        if (identifier == null || identifier.contains("@")) {
            return;
        }

        String header = (event.getCustomSubject() != null) ? "🔔 [" + event.getCustomSubject().toUpperCase() + "]" :
                switch (event.getType()) {
                    case REGISTER -> "🔑 [XÁC THỰC ĐĂNG KÝ]";
                    case SECURITY_UPDATE -> "⚠️ [CẢNH BÁO BẢO MẬT]";
                    case ACCOUNT_LOCK -> "🚫 [KHÓA TÀI KHOẢN]";
                    default -> "🔔 [THÔNG BÁO]";
                };

        String body = (event.getCustomMessage() != null) ? event.getCustomMessage() :
                switch (event.getType()) {
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
            log.info("Telegram gửi thành công tới identifier: {}", event.getIdentifier());
        } catch (Exception e) {
            log.error("Lỗi API Telegram: {}", e.getMessage());
        }
    }
}
