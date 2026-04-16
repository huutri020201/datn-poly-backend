package com.example.nhom3.project.modules.identity.dto.event;

import com.example.nhom3.project.modules.identity.enums.VerificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {
    String identifier;
    String code;
    VerificationType type;
    String targetName;
    String newValue;
}
