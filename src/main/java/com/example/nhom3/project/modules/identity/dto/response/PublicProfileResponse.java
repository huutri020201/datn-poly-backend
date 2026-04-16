package com.example.nhom3.project.modules.identity.dto.response;

import com.example.nhom3.project.modules.identity.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicProfileResponse {
    String nickname;
    Gender gender;
    String avatarUrl;
    String membershipLevel;
}
