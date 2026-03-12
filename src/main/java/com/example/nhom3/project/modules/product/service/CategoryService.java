package com.example.nhom3.project.modules.product.service;

import com.example.nhom3.project.modules.product.dto.request.CategoryRequest;
import com.example.nhom3.project.modules.product.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(UUID id);
    CategoryResponse updateCategory(UUID id, CategoryRequest request);
    void deleteCategory(UUID id);
}
