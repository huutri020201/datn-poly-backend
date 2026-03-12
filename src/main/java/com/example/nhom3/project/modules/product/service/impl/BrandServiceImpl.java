package com.example.nhom3.project.modules.product.service.impl;

import com.example.nhom3.project.modules.product.dto.request.BrandRequest;
import com.example.nhom3.project.modules.product.dto.response.BrandResponse;
import com.example.nhom3.project.modules.product.entity.Brand;
import com.example.nhom3.project.modules.product.mapper.BrandMapper;
import com.example.nhom3.project.modules.product.repository.BrandRepository;
import com.example.nhom3.project.modules.product.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;
    @Override
    public BrandResponse create(BrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("BRAND_ALREADY_EXISTS");
        }
        return brandMapper.toResponse(brandRepository.save(brandMapper.toEntity(request)));
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandMapper.toResponseList(brandRepository.findAll());
    }

    @Override
    public BrandResponse getBrandById(UUID id) {
        return brandRepository.findById(id)
                .map(brandMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("BRAND_NOT_FOUND"));
    }

    @Override
    public BrandResponse updateBrand(UUID id, BrandRequest request) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("BRAND_NOT_FOUND"));
        brand.setName(request.getName());
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(UUID id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("BRAND_NOT_FOUND");
        }
        brandRepository.deleteById(id);
    }
}
