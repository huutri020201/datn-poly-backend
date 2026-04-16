package com.example.nhom3.project.modules.product.controller;

import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.product.dto.request.BrandRequest;
import com.example.nhom3.project.modules.product.dto.response.BrandResponse;
import com.example.nhom3.project.modules.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.created(brandService.create(brandRequest),"Thêm thương hiệu thành công");
    }
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands() {
        return ApiResponse.success(brandService.getAllBrands(),"Lấy danh sách thương hiệu thành công");
    }
    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable UUID id) {
        return ApiResponse.success(brandService.getBrandById(id),"Lấy thương hiệu thành công");
    }
    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid BrandRequest request) {
        return ApiResponse.success(brandService.updateBrand(id, request), "Cập nhật sản phẩm thành công");
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ApiResponse.success(null, "Đã xóa sản phẩm thành công");
    }
}
