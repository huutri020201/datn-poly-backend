package com.example.nhom3.project.modules.identity.controller;

import com.example.nhom3.project.common.utils.SecurityUtils;
import com.example.nhom3.project.modules.identity.dto.request.AdminCreateUserRequest;
import com.example.nhom3.project.modules.identity.dto.request.AdminUserUpdateRequest;
import com.example.nhom3.project.modules.identity.dto.request.PasswordUpdateRequest;
import com.example.nhom3.project.modules.identity.dto.response.AdminUserResponse;
import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid AdminCreateUserRequest request) {
        return ApiResponse.success(userService.createUser(request), "User created successfully");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers(), "Fetched all users");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AdminUserResponse> getAdminUserDetail(@PathVariable UUID id) {
        return ApiResponse.success(userService.getAdminUserDetail(id), "Admin fetched user detail");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid AdminUserUpdateRequest request) {
        return ApiResponse.success(userService.updateUser(id, request), "User updated successfully by Admin");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ApiResponse.success(null, "User deleted successfully");
    }

    @PatchMapping("/my-password")
    public ApiResponse<Void> updateMyPassword(@RequestBody @Valid PasswordUpdateRequest request) {
        userService.updatePassword(request);
        return ApiResponse.success(null, "Password updated successfully");
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        return ApiResponse.success(userService.getUser(currentUserId), "Fetched your account info");
    }

}
