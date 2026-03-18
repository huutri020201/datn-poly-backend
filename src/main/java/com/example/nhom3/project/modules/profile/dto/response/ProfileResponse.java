package com.example.nhom3.project.modules.profile.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ProfileResponse(
        UUID identityId,
        String fullName,
        String gender,
        LocalDate dob,
        String avatarUrl,
        Integer rankPoint,
        String membershipLevel,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}


