package com.example.nhom3.project.modules.product.mapper;

import com.example.nhom3.project.modules.product.dto.request.BrandRequest;
import com.example.nhom3.project.modules.product.dto.response.BrandResponse;
import com.example.nhom3.project.modules.product.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandRequest request);
    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toResponseList(List<Brand> brands);
}