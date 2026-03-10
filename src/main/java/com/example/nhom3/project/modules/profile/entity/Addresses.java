package com.example.nhom3.project.modules.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

    @Entity
    @Table(name = "addresses")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Addresses {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(name = "profile_id")
        private UUID profileId;

        @Column(name = "receiver_name", nullable = false)
        private String receiverName;

        @Column(name = "phone_number", nullable = false)
        private String phoneNumber;

        @Column(name = "province_code")
        private String provinceCode;

        @Column(name = "district_code")
        private String districtCode;

        @Column(name = "ward_code")
        private String wardCode;

        @Column(name = "detail_address")
        private String detailAddress;

        @Column(name = "is_default")
        private Boolean isDefault;

        @Column(name = "created_at")
        private OffsetDateTime createdAt;
    }

