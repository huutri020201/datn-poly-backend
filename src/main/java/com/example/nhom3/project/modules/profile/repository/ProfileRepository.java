package com.example.nhom3.project.modules.profile.repository;

import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
}
