package com.example.nhom3.project.modules.product.mapper;

import com.example.nhom3.project.modules.product.dto.request.CategoryRequest;
import com.example.nhom3.project.modules.product.dto.response.CategoryResponse;
import com.example.nhom3.project.modules.product.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);
    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);
}
