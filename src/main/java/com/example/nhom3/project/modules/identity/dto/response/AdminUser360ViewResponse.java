package com.example.nhom3.project.modules.identity.dto.response;

import com.example.nhom3.project.modules.identity.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUser360ViewResponse {
    UUID id;
    String email;
    String phone;
    UserStatus status;
    Instant createdAt;

    String fullName;
    String nickname;
    String avatarUrl;
    Integer rankPoint;

    Set<String> roles;

    public AdminUser360ViewResponse(UUID id, String email, String phone, UserStatus status,
                                    Instant createdAt, String fullName, String nickname,
                                    String avatarUrl, Integer rankPoint) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.rankPoint = rankPoint;
    }
}
