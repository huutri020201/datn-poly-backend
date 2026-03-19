package com.example.nhom3.project.modules.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record ProfileCompleteRequest(
        String userId,
        @NotBlank(message = "FULL_NAME_REQUIRED")
        String fullName,
        String gender,
        LocalDate dob,
        String avatarUrl,
        @NotBlank(message = "PHONE_REQUIRED")
        @Pattern(regexp = "^[0-9]{10}$", message = "INVALID_PHONE_FORMAT")
        String phone,
        @NotBlank(message = "ADDRESS_REQUIRED")
        String address
) {
}