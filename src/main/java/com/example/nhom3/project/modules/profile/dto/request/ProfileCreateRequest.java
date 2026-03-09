package com.example.nhom3.project.modules.profile.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileCreateRequest(
        UUID identityId,
        String fullName,
        String gender,
        LocalDate dob,
        String avatarUrl
) {
}
