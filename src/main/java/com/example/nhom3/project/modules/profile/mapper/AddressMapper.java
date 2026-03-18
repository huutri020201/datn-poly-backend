package com.example.nhom3.project.modules.profile.mapper;

import com.example.nhom3.project.modules.profile.dto.request.AddressRequest;
import com.example.nhom3.project.modules.profile.dto.response.AddressResponse;
import com.example.nhom3.project.modules.profile.entity.Addresses;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Addresses toEntity(AddressRequest request);
    AddressResponse toResponse(Addresses entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(AddressRequest request, @MappingTarget Addresses entity);
}