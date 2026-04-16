package com.example.nhom3.project.modules.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class MyProfileResponse extends PublicProfileResponse {
    UUID identityId;
    String fullName;
    LocalDate dob;
    Integer rankPoint;
    Instant createdAt;
}
