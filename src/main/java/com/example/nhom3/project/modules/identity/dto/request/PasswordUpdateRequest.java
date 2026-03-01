package com.example.nhom3.project.modules.identity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordUpdateRequest {
    String oldPassword;
    String newPassword;
}
