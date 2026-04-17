package com.example.nhom3.project.modules.identity.repository;


import com.example.nhom3.project.modules.identity.entity.UserRole;
import com.example.nhom3.project.modules.identity.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    @Query("SELECT ur.user.id, r.name FROM UserRole ur JOIN ur.role r WHERE ur.user.id IN :userIds")
    List<Object[]> findRolesByUserIds(@Param("userIds") List<UUID> userIds);
}
