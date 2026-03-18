package com.example.nhom3.project.modules.profile.service.Impl;

import com.example.nhom3.project.modules.profile.dto.request.ProfileCreateRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import com.example.nhom3.project.modules.profile.mapper.ProfileMapper;
import com.example.nhom3.project.modules.profile.repository.ProfileRepository;
import com.example.nhom3.project.modules.profile.service.ProfileService;
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

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    @Override
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        ProfileEntity profile = profileMapper.toEntity(request);
        ProfileEntity saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved);
    }
    @Override
    public ProfileResponse getUserProfile(UUID userId) {

        ProfileEntity profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return profileMapper.toResponse(profile);
    }
    @Override
    public ProfileResponse updateProfile(UUID userId, ProfileUpdateRequest request) {

        ProfileEntity profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profileMapper.updateEntity(request, profile);

        ProfileEntity updated = profileRepository.save(profile);

        return profileMapper.toResponse(updated);
    }

    @Override
    public void deleteProfile(UUID id) {
        profileRepository.deleteById(id);
    }
}