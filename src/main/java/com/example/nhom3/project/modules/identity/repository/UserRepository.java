package com.example.nhom3.project.modules.identity.repository;

import com.example.nhom3.project.modules.identity.dto.response.AdminUser360ViewResponse;
import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role " +
            "WHERE u.email = :identifier OR u.phone = :identifier")
    Optional<User> findByIdentifierWithRoles(@Param("identifier") String identifier);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.phone = :identifier")
    Optional<User> findByEmailOrPhone(@Param("identifier") String identifier);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.id = :id")
    Optional<User> findByIdWithRoles(@Param("id") UUID id);

    @Query("SELECT u FROM User u WHERE u.status = :status AND u.deletedAt <= :threshold")
    List<User> findExpiredSoftDeletedUsers(
            @Param("status") UserStatus status,
            @Param("threshold") Instant threshold
    );

    @Modifying
    @Transactional
    long deleteByStatusAndCreatedAtBefore(String status, Instant dateTime);

    @Query("""
    SELECT DISTINCT new com.example.nhom3.project.modules.identity.dto.response.AdminUser360ViewResponse(
        u.id, 
        u.email, 
        u.phone, 
        u.status, 
        u.createdAt,
        p.fullName, 
        p.nickname, 
        p.avatarUrl, 
        p.rankPoint
    )
    FROM User u
    LEFT JOIN Profile p ON p.user.id = u.id
    LEFT JOIN u.userRoles ur
    LEFT JOIN ur.role r
    WHERE
        (:keyword IS NULL OR :keyword = '' OR 
            LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(COALESCE(p.fullName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    AND (:status IS NULL OR u.status = :status)
    AND (:role IS NULL OR :role = '' OR r.name = :role)
    """)
    Page<AdminUser360ViewResponse> findAllUsersDetailed(
            @Param("keyword") String keyword,
            @Param("status") UserStatus status,
            @Param("role") String role,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id IN :ids")
    void updateStatusInBulk(@Param("ids") List<UUID> ids, @Param("status") UserStatus status);

}
