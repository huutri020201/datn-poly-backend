package com.example.nhom3.project.modules.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEntity {
    @Id
    @Column(name = "identity_id")
    private UUID identityId;

    @Column(name = "full_name")
    private String fullName;

    private String gender;

    private LocalDate dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "rank_point")
    private Integer rankPoint;

    @Column(name = "membership_level")
    private String membershipLevel;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
