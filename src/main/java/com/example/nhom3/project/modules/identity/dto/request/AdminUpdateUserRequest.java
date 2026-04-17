package com.example.nhom3.project.modules.identity.dto.request;

import com.example.nhom3.project.modules.identity.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUpdateUserRequest {
    @Email(message = "EMAIL_INVALID")
    String email;

    @Pattern(regexp = "^\\d{10}$", message = "PHONE_INVALID")
    String phone;
    String password;
    UserStatus status;
    Boolean twoFactorEnabled;
    Boolean isMfaVerified;
    Integer failedAttemptCount;
    Instant lockedUntil;
    String banReason;
    Set<String> roles;
}
