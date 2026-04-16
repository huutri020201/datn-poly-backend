package com.example.nhom3.project.modules.identity.service;


import com.example.nhom3.project.modules.identity.dto.event.UserActivatedEvent;
import com.example.nhom3.project.modules.identity.dto.request.AdminUpdateProfileRequest;
import com.example.nhom3.project.modules.identity.dto.request.UpdateProfileRequest;
import com.example.nhom3.project.modules.identity.dto.response.MyProfileResponse;
import com.example.nhom3.project.modules.identity.dto.response.PublicProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileService {
    void handleUserActivatedEvent(UserActivatedEvent event);
    MyProfileResponse getMyProfile();
    PublicProfileResponse getProfile(UUID targetId);

    MyProfileResponse updateMyProfile(UpdateProfileRequest request);

    MyProfileResponse updateProfileInternal(UUID targetId, AdminUpdateProfileRequest request);

    String uploadAvatar(MultipartFile file);
}
