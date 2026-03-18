package com.example.nhom3.project.modules.profile.dto.response;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String receiverName,
        String phoneNumber,
        String provinceCode,
        String districtCode,
        String wardCode,
        String detailAddress,
        Boolean isDefault
) {}