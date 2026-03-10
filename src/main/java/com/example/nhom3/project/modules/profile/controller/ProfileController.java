package com.example.nhom3.project.modules.profile.controller;

import com.example.nhom3.project.modules.profile.dto.ApiResponse;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ApiResponse<ProfileResponse> getUserProfile(@PathVariable UUID userId) {
        ProfileResponse profile = profileService.getUserProfile(userId);
        return ApiResponse.success(profile, "Get profile successfully");
    }

    @PutMapping("/{userId}")
    public ApiResponse<ProfileResponse> updateProfile(
            @PathVariable UUID userId,
            @RequestBody ProfileUpdateRequest request
    ) {
        ProfileResponse profile = profileService.updateProfile(userId, request);
        return ApiResponse.success(profile, "Update profile successfully");
    }

    @PostMapping
    public ApiResponse<ProfileResponse> createProfile(@RequestBody ProfileEntity profile) {
        ProfileResponse profileResponse = profileService.createProfile(profile);
        return ApiResponse.success(profileResponse, "Create profile successfully");
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteProfile(@PathVariable UUID userId) {
        profileService.deleteProfile(userId);
        return ApiResponse.success("Profile deleted successfully");
    }
}
