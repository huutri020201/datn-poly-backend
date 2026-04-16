package com.example.nhom3.project.modules.identity.dto.request;

import com.example.nhom3.project.modules.identity.enums.VerificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyRequest {

    @NotBlank(message = "IDENTIFIER_REQUIRED")
    String identifier;

    @NotBlank(message = "CODE_REQUIRED")
    String code;

    @NotNull(message = "VERIFICATION_TYPE_REQUIRED")
    VerificationType type;
}