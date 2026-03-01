package com.example.nhom3.project.modules.identity.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsOtpEvent {
    String phone;
    String otpCode;
}
