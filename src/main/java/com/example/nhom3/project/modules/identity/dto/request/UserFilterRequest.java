package com.example.nhom3.project.modules.identity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterRequest {
    int page = 1;
    int size = 10;

    String keyword;
    String role;
    String status;
    String level;
}
