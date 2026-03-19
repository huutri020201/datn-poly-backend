package com.example.nhom3.project.modules.profile.service;

import com.example.nhom3.project.modules.profile.dto.request.ProfileCompleteRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileCreateRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;

import java.util.UUID;

public interface ProfileService {
    ProfileResponse createProfile(ProfileCreateRequest request);
    ProfileResponse getUserProfile(UUID userId);
    ProfileResponse updateProfile(UUID userId, ProfileUpdateRequest request);
    void deleteProfile(UUID id);
    ProfileResponse completeInitialProfile(UUID userId, ProfileCompleteRequest request);
}
