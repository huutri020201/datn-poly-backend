package com.example.nhom3.project.modules.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateIdentifierRequest {
    @Pattern(regexp = "^$|^[0-9]{10}$", message = "INVALID_PHONE_FORMAT")
    String phone;

    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    String currentPassword;
}
