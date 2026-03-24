package com.example.nhom3.project.modules.profile.service.Impl;

import com.example.nhom3.project.modules.profile.dto.request.ProfileCompleteRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileCreateRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.entity.Addresses;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import com.example.nhom3.project.modules.profile.mapper.ProfileMapper;
import com.example.nhom3.project.modules.profile.repository.AddressesRepository;
import com.example.nhom3.project.modules.profile.repository.ProfileRepository;
import com.example.nhom3.project.modules.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    AddressesRepository addressRepository;
    @Override
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        ProfileEntity profile = profileMapper.toEntity(request);
        ProfileEntity saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved);
    }
    @Override
    public ProfileResponse getUserProfile(UUID userId) {

        ProfileEntity profile = profileRepository.findById(userId)
                .orElseGet(() -> {
                    log.info("Profile chưa tồn tại cho {}, tiến hành tự động tạo mới...", userId);

                    ProfileEntity newProfile = new ProfileEntity();
                    newProfile.setIdentityId(userId);
                    newProfile.setRankPoint(0);
                    newProfile.setMembershipLevel("BRONZE");
                    newProfile.setFullName("Người dùng mới");
                    return profileRepository.save(newProfile);
                });

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

    @Transactional
    @Override
    public ProfileResponse completeInitialProfile(UUID userId, ProfileCompleteRequest request) {
        ProfileEntity profile = profileRepository.findById(userId)
                .orElse(new ProfileEntity());

        profile.setIdentityId(userId);
        profile.setFullName(request.fullName());
        profile.setGender(request.gender());
        profile.setDob(request.dob());
        profile.setAvatarUrl(request.avatarUrl());
        profile.setCreatedAt(OffsetDateTime.now());
        profile.setUpdatedAt(OffsetDateTime.now());

        ProfileEntity savedProfile = profileRepository.save(profile);

        Addresses address = Addresses.builder()
                .profileId(userId)
                .receiverName(request.fullName())
                .phoneNumber(request.phone())
                .detailAddress(request.address())
                .isDefault(true)
                .createdAt(OffsetDateTime.now())
                .build();

        addressRepository.save(address);

        return profileMapper.toResponse(savedProfile);
    }
}