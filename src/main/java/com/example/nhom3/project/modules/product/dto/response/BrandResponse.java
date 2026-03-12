package com.example.nhom3.project.modules.product.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {
    UUID id;
    String name;
    String slug;
}