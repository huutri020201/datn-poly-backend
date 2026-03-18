package com.example.nhom3.project.modules.profile.controller;

import com.example.nhom3.project.modules.profile.dto.ApiResponse;
import com.example.nhom3.project.modules.profile.dto.request.ProfileCreateRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile(Authentication authentication) {

        String userId = authentication.getName();

        return ApiResponse.success(
                profileService.getUserProfile(UUID.fromString(userId)),
                "Get profile successfully"
        );
    }
    @PutMapping("/{userId}")
    public ApiResponse<ProfileResponse> updateProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {

        return ApiResponse.success(
                profileService.updateProfile(userId, request),
                "Update profile successfully"
        );
    }

    @PostMapping
    public ApiResponse<ProfileResponse> createProfile(
            @Valid @RequestBody ProfileCreateRequest request
    ) {
        return ApiResponse.success(
                profileService.createProfile(request),
                "Create profile successfully"
        );
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteProfile(@PathVariable UUID userId) {

        profileService.deleteProfile(userId);

        return ApiResponse.successMessage("Profile deleted successfully");
    }
}