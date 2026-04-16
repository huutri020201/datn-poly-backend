package com.example.nhom3.project.modules.identity.listener;


import com.example.nhom3.project.modules.identity.dto.event.NotificationEvent;
import com.example.nhom3.project.modules.identity.service.EmailService;
import com.example.nhom3.project.modules.identity.service.TelegramSmsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityNotificationListener {

    EmailService emailService;
    TelegramSmsService telegramService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotification(NotificationEvent event) {
        String id = event.getIdentifier();
        if (id == null) return;

        log.info("Xử lý thông báo cho: {}", id);

        if (id.contains("@")) {
            emailService.sendEmail(event);
        } else {
            telegramService.sendNotification(event);
        }
    }
}