package com.example.nhom3.project.modules.identity.controller;

import com.example.nhom3.project.modules.identity.dto.request.AdminCreateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.AdminUpdateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.ChangePasswordRequest;
import com.example.nhom3.project.modules.identity.dto.request.UpdateIdentifierRequest;
import com.example.nhom3.project.modules.identity.dto.response.AdminUserResponse;
import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.identity.dto.response.PageResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.service.UserService;
import com.example.nhom3.project.common.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    // --- NHÓM ADMIN (Quản trị hệ thống) ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> createUser(@RequestBody @Valid AdminCreateUserRequest request) {
        return ApiResponse.success(userService.createUser(request), "Tạo người dùng mới thành công");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PageResponse<AdminUserResponse>> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ApiResponse.success(userService.getAllUsers(page, size), "Lấy danh sách người dùng phân trang");
    }

    @GetMapping("/{id}/admin-detail")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> getAdminUserDetail(@PathVariable UUID id) {
        return ApiResponse.success(userService.getAdminUserDetail(id), "Lấy chi tiết quản trị người dùng");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> updateUserByAdmin(
            @PathVariable UUID id,
            @RequestBody @Valid AdminUpdateUserRequest request) {
        return ApiResponse.success(userService.updateUser(id, request), "Admin cập nhật người dùng thành công");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> softDeleteByAdmin(@PathVariable UUID id) {
        userService.adminSoftDelete(id);
        return ApiResponse.success(null, "Đã đưa người dùng vào danh sách chờ xóa (30 ngày)");
    }

    @PostMapping("/{id}/ban")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> banUser(
            @PathVariable UUID id,
            @RequestBody @Valid AdminUpdateUserRequest request) {
        return ApiResponse.success(
                userService.banUser(id, request.getLockedUntil(), request.getBanReason()),
                "Đã khóa tài khoản người dùng"
        );
    }

    @PostMapping("/{id}/unban") // Đổi tên cho rõ ràng: unban thay vì restore
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> unbanUser(@PathVariable UUID id) {
        return ApiResponse.success(userService.unbanUser(id), "Mở khóa tài khoản thành công");
    }

    @PostMapping("/{id}/admin-restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> restoreByAdmin(@PathVariable UUID id) {
        return ApiResponse.success(userService.restoreUserByAdmin(id), "Khôi phục trạng thái xóa bởi Admin");
    }

    // --- NHÓM USER (Cá nhân tự quản lý) ---

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo(), "Lấy thông tin cá nhân thành công");
    }

    @PatchMapping("/my-password")
    public ApiResponse<Void> updateMyPassword(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.success(null, "Đổi mật khẩu thành công");
    }

    @PatchMapping("/identity")
    public ApiResponse<UserResponse> updateMyIdentity(@RequestBody @Valid UpdateIdentifierRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserResponse result = userService.updateIdentity(currentUserId, request);
        return ApiResponse.success(result, "Cập nhật Email/SĐT thành công");
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> selfDelete() {
        userService.userSelfDelete();
        return ApiResponse.success(null, "Tài khoản của bạn sẽ bị xóa vĩnh viễn sau 30 ngày nếu không đăng nhập lại");
    }

    @PostMapping("/my-restore")
    public ApiResponse<UserResponse> restoreSelf() {
        return ApiResponse.success(userService.restoreSelf(), "Chào mừng bạn trở lại!");
    }
}
