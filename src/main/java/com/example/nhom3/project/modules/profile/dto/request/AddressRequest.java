package com.example.nhom3.project.modules.profile.dto.request;

public record AddressRequest(
        String receiverName,
        String phoneNumber,
        String provinceCode,
        String districtCode,
        String wardCode,
        String detailAddress,
        Boolean isDefault
) {}