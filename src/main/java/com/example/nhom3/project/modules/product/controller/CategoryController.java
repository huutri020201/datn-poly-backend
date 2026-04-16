package com.example.nhom3.project.modules.product.controller;

import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.product.dto.request.CategoryRequest;
import com.example.nhom3.project.modules.product.dto.response.CategoryResponse;
import com.example.nhom3.project.modules.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;
    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.created(categoryService.create(request),"Thêm thương hiệu thành công");
    }
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategory() {
        return ApiResponse.success(categoryService.getAllCategories(),"Lấy danh sách thương hiệu thành công");
    }
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable UUID id) {
        return ApiResponse.success(categoryService.getCategoryById(id),"Lấy thương hiệu thành công");
    }
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequest request) {
        return ApiResponse.success(categoryService.updateCategory(id, request), "Cập nhật sản phẩm thành công");
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success(null, "Đã xóa sản phẩm thành công");
    }
}
