package com.example.nhom3.project.modules.identity.repository;


import com.example.nhom3.project.modules.identity.entity.UserRole;
import com.example.nhom3.project.modules.identity.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
