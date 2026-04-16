package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.modules.identity.dto.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    JavaMailSender mailSender;

    public void sendEmail(NotificationEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getIdentifier());

            String subject = switch (event.getType()) {
                case REGISTER -> "Xác thực tài khoản mới";
                case SECURITY_UPDATE -> "Cảnh báo thay đổi thông tin";
                case ACCOUNT_LOCK -> "Tài khoản của bạn đã bị khóa";
                case ACCOUNT_UNLOCK -> "Tài khoản của bạn đã được mở khóa";
                default -> "Thông báo từ IdentityPro";
            };

            String content = switch (event.getType()) {
                case REGISTER -> "Vui lòng click vào link để xác thực: http://localhost:5173/verify?token=" + event.getCode();
                case SECURITY_UPDATE -> String.format("Thông tin [%s] của bạn đã được cập nhật thành: %s",
                        event.getTargetName(), event.getNewValue());
                case ACCOUNT_LOCK -> String.format("Tài khoản bị khóa vì lý do: %s. Hiệu lực đến: %s",
                        event.getTargetName(), event.getNewValue());
                default -> "Mã xác thực của bạn là: " + event.getCode();
            };

            message.setSubject("[IdentityPro] " + subject);
            message.setText(content);
            mailSender.send(message);
            log.info("Email gửi thành công tới: {}", event.getIdentifier());
        } catch (Exception e) {
            log.error("Lỗi gửi Email: {}", e.getMessage());
        }
    }
}
