package com.example.nhom3.project.modules.product.service.impl;

import com.example.nhom3.project.modules.product.dto.request.CategoryRequest;
import com.example.nhom3.project.modules.product.dto.response.CategoryResponse;
import com.example.nhom3.project.modules.product.entity.Category;
import com.example.nhom3.project.modules.product.mapper.CategoryMapper;
import com.example.nhom3.project.modules.product.repository.CategoryRepository;
import com.example.nhom3.project.modules.product.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("BRAND_ALREADY_EXISTS");
        }
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("CATEGORY_NOT_FOUND"));
    }

    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("CATEGORY_NOT_FOUND"));
        category.setName(request.getName());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("PRODUCT_NOT_FOUND");
        }
        categoryRepository.deleteById(id);
    }
}
