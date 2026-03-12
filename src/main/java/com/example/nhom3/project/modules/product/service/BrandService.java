package com.example.nhom3.project.modules.product.service;

import com.example.nhom3.project.modules.product.dto.request.BrandRequest;
import com.example.nhom3.project.modules.product.dto.response.BrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    BrandResponse create(BrandRequest request);
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandById(UUID id);
    BrandResponse updateBrand(UUID id, BrandRequest request);
    void deleteBrand(UUID id);
}
