package com.example.nhom3.project.modules.identity.dto.payload;

import lombok.Builder;

import java.util.Map;

@Builder
public record SecurityCodePayload(
        String code,
        Map<String, Object> metadata
) {}
