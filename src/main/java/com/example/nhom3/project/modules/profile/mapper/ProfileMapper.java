package com.example.nhom3.project.modules.profile.mapper;

import com.example.nhom3.project.modules.profile.dto.request.ProfileCompleteRequest; // Thêm DTO tổng hợp
import com.example.nhom3.project.modules.profile.dto.request.ProfileCreateRequest;
import com.example.nhom3.project.modules.profile.dto.request.ProfileUpdateRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.entity.ProfileEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponse toResponse(ProfileEntity entity);

    @Mapping(target = "identityId", ignore = true)
    @Mapping(target = "rankPoint", ignore = true)
    @Mapping(target = "membershipLevel", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProfileEntity toEntity(ProfileCreateRequest request);
    @Mapping(target = "identityId", ignore = true)
    @Mapping(target = "rankPoint", ignore = true)
    @Mapping(target = "membershipLevel", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "avatarUrl", source = "avatarUrl")
    ProfileEntity toEntityFromComplete(ProfileCompleteRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProfileUpdateRequest request, @MappingTarget ProfileEntity entity);
}