package com.example.nhom3.project.modules.profile.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProfileUpdateRequest(
        @NotBlank(message = "Full name is required")
        String fullName,
        String gender,
        LocalDate dob,
        String avatarUrl
) {
}