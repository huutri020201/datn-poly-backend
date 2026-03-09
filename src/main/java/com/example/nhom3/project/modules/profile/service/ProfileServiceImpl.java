package com.example.nhom3.project.modules.profile.service;


import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public ProfileResponse getUserProfile(UUID id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }
    @Override
    public ProfileResponse updateProfile(UUID userId, ProfileUpdateRequest request) {

        ProfileEntity profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setFullName(request.fullName());
        profile.setGender(request.gender());
        profile.setDob(request.dob());
        profile.setAvatarUrl(request.avatarUrl());

        profileRepository.save(profile);

        return mapToResponse(profile);
    }

    @Override
    public ProfileResponse createProfile(ProfileEntity profile) {
        ProfileEntity savedProfile;
        savedProfile = profileRepository.save(profile);
        return mapToResponse(savedProfile);
    }
    @Override
    public void deleteProfile(UUID id) {
        profileRepository.deleteById(id);
        }

    private ProfileResponse mapToResponse(ProfileEntity profile) {
        return ProfileResponse.builder()
                .identityId(profile.getIdentityId())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .dob(profile.getDob())
                .avatarUrl(profile.getAvatarUrl())
                .rankPoint(profile.getRankPoint())
                .membershipLevel(profile.getMembershipLevel())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}

