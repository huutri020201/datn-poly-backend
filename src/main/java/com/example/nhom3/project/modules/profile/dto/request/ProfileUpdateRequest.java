package com.example.nhom3.project.modules.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProfileUpdateRequest(
        @NotBlank String name,
        String fullName,
        String gender,
        LocalDate dob,
        String avatarUrl
) {
}
