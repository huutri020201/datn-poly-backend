package com.example.nhom3.project.modules.identity.service.Impl;



import com.example.nhom3.project.modules.identity.dto.event.NotificationEvent;
import com.example.nhom3.project.modules.identity.dto.request.*;
import com.example.nhom3.project.modules.identity.dto.response.AdminUser360ViewResponse;
import com.example.nhom3.project.modules.identity.dto.response.AdminUserResponse;
import com.example.nhom3.project.modules.identity.dto.response.PageResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.entity.Role;
import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.enums.UserStatus;
import com.example.nhom3.project.modules.identity.enums.VerificationType;
import com.example.nhom3.project.common.exception.AppException;
import com.example.nhom3.project.common.exception.ErrorCode;
import com.example.nhom3.project.modules.identity.mapper.UserMapper;
import com.example.nhom3.project.modules.identity.repository.RoleRepository;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import com.example.nhom3.project.modules.identity.repository.UserRoleRepository;
import com.example.nhom3.project.modules.identity.service.UserService;
import com.example.nhom3.project.common.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserRoleRepository userRoleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse createUser(AdminCreateUserRequest request) {

        String email = StringUtils.hasText(request.getEmail())
                ? request.getEmail().trim()
                : null;

        String phone = StringUtils.hasText(request.getPhone())
                ? request.getPhone().trim()
                : null;

        if (email == null && phone == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        if (email != null && userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (phone != null && userRepository.existsByPhone(phone)) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        User user = userMapper.toUser(request);

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setFailedAttemptCount(0);

        if (!CollectionUtils.isEmpty(request.getRoles())) {
            request.getRoles().forEach(roleName -> {
                String formattedRoleName = roleName.startsWith("ROLE_")
                        ? roleName
                        : "ROLE_" + roleName;

                Role role = roleRepository.findByName(formattedRoleName)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

                user.addRole(role);
            });
        }

        return userMapper.toAdminUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<AdminUserResponse> getAllUsers(int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        List<AdminUserResponse> dtoList = userPage.getContent().stream()
                .map(userMapper::toAdminUserResponse)
                .toList();
        return PageResponse.<AdminUserResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .hasNext(userPage.hasNext())
                .hasPrevious(userPage.hasPrevious())
                .data(dtoList)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse getAdminUserDetail(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toAdminUserResponse(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse updateUser(UUID userId, AdminUpdateUserRequest request) {

        UUID currentAdminId = SecurityUtils.getCurrentUserId();

        if (userId.equals(currentAdminId)) {
            User currentAdminInDb = userRepository.findById(currentAdminId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            UserStatus statusRequest = request.getStatus();
            UserStatus statusInDb = currentAdminInDb.getStatus();
            if (statusRequest != null && statusRequest != statusInDb) {
                throw new AppException(ErrorCode.CANNOT_CHANGE_SELF_STATUS);
            }
            boolean hasAdminRole = request.getRoles().stream()
                    .anyMatch(role -> role.equalsIgnoreCase("ROLE_ADMIN"));

            if (!hasAdminRole) {
                throw new AppException(ErrorCode.CANNOT_REMOVE_ADMIN_ROLE);
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (StringUtils.hasText(request.getEmail()) && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
            user.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone()) && !request.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new AppException(ErrorCode.PHONE_EXISTED);
            }
            user.setPhone(request.getPhone());
        }

       userMapper.updateUser(user, request);
        if (StringUtils.hasText(request.getPassword())) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllByNameIn(request.getRoles());
            if (roles.size() != request.getRoles().size()) {
                throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
            }
            user.getUserRoles().clear();
            roles.forEach(user::addRole);
        }
        if (request.getFailedAttemptCount() != null && request.getFailedAttemptCount() == 0) {
            user.setLockedUntil(null);
        }
        if (user.getStatus() == UserStatus.ACTIVE) {
            user.setBanReason(null);
        } else if (user.getStatus() == UserStatus.BANNED) {
            if (StringUtils.hasText(request.getBanReason())) {
                user.setBanReason(request.getBanReason());
            }
        }

        return userMapper.toAdminUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void adminSoftDelete(UUID targetId) {
        User user = userRepository.findById(targetId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setDeletedAt(Instant.now());
        user.setDeletedByAdmin(true);
        user.setStatus(UserStatus.DELETED);

        userRepository.save(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse restoreUserByAdmin(UUID targetId) {
        User user = userRepository.findById(targetId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setDeletedAt(null);
        user.setDeletedByAdmin(false);

        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(Instant.now())) {
            user.setStatus(UserStatus.BANNED);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }

        return userMapper.toAdminUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse banUser(UUID targetId, Instant until, String reason) {
        User user = userRepository.findById(targetId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setLockedUntil(until);
        user.setBanReason(reason);
        user.setStatus(UserStatus.BANNED);

        userRepository.save(user);

        eventPublisher.publishEvent(NotificationEvent.builder()
                .identifier(user.getEmail())
                .type(VerificationType.ACCOUNT_LOCK)
                .targetName(reason)
                .newValue(until.toString())
                .build());

        return userMapper.toAdminUserResponse(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse unbanUser(UUID targetId) {
        User user = userRepository.findById(targetId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setLockedUntil(null);
        user.setBanReason(null);

        if (user.getDeletedAt() == null) {
            user.setStatus(UserStatus.ACTIVE);
        }

        userRepository.save(user);
        eventPublisher.publishEvent(NotificationEvent.builder()
                .identifier(user.getEmail())
                .type(VerificationType.ACCOUNT_UNLOCK)
                .targetName("Trạng thái tài khoản")
                .newValue("Đã được mở khóa")
                .build());

        return userMapper.toAdminUserResponse(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void hardDeleteUser(UUID targetId) {
        if (!userRepository.existsById(targetId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(targetId);
    }

    // Ban/Unban: Dùng Enum UserStatus bên trong logic
//    @Override
//    public AdminUserResponse updateStatus(UUID targetId, UserStatus status, String reason, Instant until) {
//
//    }

    // --- NHÓM NGƯỜI DÙNG (SELF-SERVICE) ---
    @Override
    public UserResponse getMyInfo() {
        UUID currentId = SecurityUtils.getCurrentUserId();
        return userRepository.findById(currentId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        UUID currentId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(currentId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD) {
                @Override
                public String getMessage() {
                    return "Mật khẩu cũ không chính xác.";
                }
            };
        }
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        publishSecurityEvent(user, "Mật khẩu", "********");
    }

    @Override
    public void userSelfDelete() {
        UUID userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setDeletedAt(Instant.now());
        user.setDeletedByAdmin(false);
        user.setStatus(UserStatus.PENDING_DELETION);
        userRepository.save(user);
    }

    @Override
    public UserResponse restoreSelf() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.getDeletedAt() != null) {
            long daysInTrash = java.time.Duration.between(user.getDeletedAt(), Instant.now()).toDays();
            if (daysInTrash > 30) {
                throw new AppException(ErrorCode.USER_DELETED_PERMANENTLY);
            }
        }

        user.setDeletedAt(null);
        user.setDeletedByAdmin(false);
        user.setStatus(UserStatus.ACTIVE);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateIdentity(UUID userId, UpdateIdentifierRequest request) {

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        if (!userId.equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        String oldEmail = user.getEmail();
        String oldPhone = user.getPhone();
        boolean isChanged = false;

        if (StringUtils.hasText(request.getPhone()) && !request.getPhone().equals(oldPhone)) {
            if (userRepository.existsByPhone(request.getPhone())) throw new AppException(ErrorCode.PHONE_EXISTED);
            user.setPhone(request.getPhone());
            isChanged = true;
            publishSecurityEvent(user, "Số điện thoại", request.getPhone());
        }

        if (StringUtils.hasText(request.getEmail()) && !request.getEmail().equals(oldEmail)) {
            if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.EMAIL_EXISTED);
            user.setEmail(request.getEmail());
            isChanged = true;
            publishSecurityEvent(user, "Email đăng nhập", request.getEmail());
        }

        if (!isChanged) {
            return userMapper.toUserResponse(user);
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<AdminUser360ViewResponse> getAdminUserList(int page, int size, String keyword, UserStatus status, String role) {
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String filterRole = (role != null && !role.trim().isEmpty()) ? role.trim() : null;

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<AdminUser360ViewResponse> usersPage = userRepository.findAllUsersDetailed(searchKeyword, status, filterRole, pageable);
        List<AdminUser360ViewResponse> userList = usersPage.getContent();

        if (!userList.isEmpty()) {
            List<UUID> userIds = userList.stream()
                    .map(AdminUser360ViewResponse::getId)
                    .toList();

            List<Object[]> rolesData = userRoleRepository.findRolesByUserIds(userIds);

            userList.forEach(userDto -> {
                Set<String> roles = rolesData.stream()
                        .filter(row -> row[0].equals(userDto.getId()))
                        .map(row -> (String) row[1])
                        .collect(Collectors.toSet());
                userDto.setRoles(roles);
            });
        }

        return PageResponse.<AdminUser360ViewResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(usersPage.getTotalPages())
                .totalElements(usersPage.getTotalElements())
                .data(userList)
                .hasNext(usersPage.hasNext())
                .hasPrevious(usersPage.hasPrevious())
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void bulkUpdateStatus(List<UUID> ids, UserStatus status) {
        userRepository.updateStatusInBulk(ids, status);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void sendBulkNotification(List<UUID> ids, String subject, String content, VerificationType type) {
        List<User> users = userRepository.findAllById(ids);

        for (User user : users) {
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                eventPublisher.publishEvent(NotificationEvent.builder()
                        .identifier(user.getEmail())
                        .customSubject(subject)
                        .customMessage(content)
                        .type(type)
                        .build());
            }
            if (user.getPhone() != null && !user.getPhone().isBlank()) {
                eventPublisher.publishEvent(NotificationEvent.builder()
                        .identifier(user.getPhone())
                        .customSubject(subject)
                        .customMessage(content)
                        .type(type)
                        .build());
            }
        }
    }



    private void publishSecurityEvent(User user, String target, String newValue) {
        eventPublisher.publishEvent(NotificationEvent.builder()
                .identifier(user.getEmail())
                .type(VerificationType.SECURITY_UPDATE)
                .targetName(target)
                .newValue(newValue)
                .build());

        // Nếu có số điện thoại, bắn thêm 1 event nữa cho số điện thoại
        if (StringUtils.hasText(user.getPhone())) {
            eventPublisher.publishEvent(NotificationEvent.builder()
                    .identifier(user.getPhone())
                    .type(VerificationType.SECURITY_UPDATE)
                    .targetName(target)
                    .newValue(newValue)
                    .build());
        }
    }
}
