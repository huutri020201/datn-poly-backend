package com.example.nhom3.project.modules.identity.dto.request;

import com.example.nhom3.project.modules.identity.enums.VerificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BulkNotifyRequest {
    List<UUID> ids;
    String customSubject;
    String customMessage;
    VerificationType type;
}
