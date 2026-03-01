package com.example.nhom3.project.modules.identity.service;



import com.example.nhom3.project.modules.identity.dto.request.AdminCreateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.AdminUserUpdateRequest;
import com.example.nhom3.project.modules.identity.dto.request.PasswordUpdateRequest;
import com.example.nhom3.project.modules.identity.dto.response.AdminUserResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(AdminCreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUser(UUID id);

    void updatePassword(PasswordUpdateRequest request);

    // Admin cập nhật thông tin User
    UserResponse updateUser(UUID userId, AdminUserUpdateRequest request);

    void deleteUser(UUID id);

    AdminUserResponse getAdminUserDetail(UUID id);
}

