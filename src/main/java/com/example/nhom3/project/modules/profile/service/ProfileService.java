package com.example.nhom3.project.modules.profile.service;

import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;

import java.util.UUID;

public interface ProfileService {
    ProfileResponse getUserProfile(UUID id);

    ProfileResponse updateProfile(UUID userId, ProfileUpdateRequest request);
    ProfileResponse createProfile(ProfileEntity profile);
    void deleteProfile(UUID id);
}
