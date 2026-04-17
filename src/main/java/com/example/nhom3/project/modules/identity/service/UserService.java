package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.modules.identity.dto.request.AdminCreateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.AdminUpdateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.ChangePasswordRequest;
import com.example.nhom3.project.modules.identity.dto.request.UpdateIdentifierRequest;
import com.example.nhom3.project.modules.identity.dto.response.AdminUser360ViewResponse;
import com.example.nhom3.project.modules.identity.dto.response.AdminUserResponse;
import com.example.nhom3.project.modules.identity.dto.response.PageResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.enums.UserStatus;
import com.example.nhom3.project.modules.identity.enums.VerificationType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserService {
    // --- NHÓM ADMIN ---
    AdminUserResponse createUser(AdminCreateUserRequest request);
    PageResponse<AdminUserResponse> getAllUsers(int page, int size);
    AdminUserResponse getAdminUserDetail(UUID id);
    AdminUserResponse updateUser(UUID userId, AdminUpdateUserRequest request);

    // Điều khiển trạng thái
    void adminSoftDelete(UUID targetId);
    AdminUserResponse restoreUserByAdmin(UUID targetId);
    AdminUserResponse banUser(UUID targetId, Instant until, String reason);
    AdminUserResponse unbanUser(UUID targetId);
    void hardDeleteUser(UUID targetId);

    // Ban/Unban: Dùng Enum UserStatus bên trong logic
//    AdminUserResponse updateStatus(UUID targetId, UserStatus status, String reason, Instant until);

    // --- NHÓM NGƯỜI DÙNG (SELF-SERVICE) ---
    UserResponse getMyInfo();
    void changePassword(ChangePasswordRequest request);
    void userSelfDelete();
    UserResponse restoreSelf();

    UserResponse updateIdentity(UUID userId, UpdateIdentifierRequest request);

    PageResponse<AdminUser360ViewResponse> getAdminUserList(int page, int size, String keyword, UserStatus status, String role);

    void bulkUpdateStatus(List<UUID> ids, UserStatus status);
    void sendBulkNotification(List<UUID> ids, String subject, String content, VerificationType type);
}
