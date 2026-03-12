package com.example.nhom3.project.modules.product.mapper;

import com.example.nhom3.project.modules.product.dto.request.ProductRequest;
import com.example.nhom3.project.modules.product.dto.request.VariantRequest;
import com.example.nhom3.project.modules.product.dto.response.ProductResponse;
import com.example.nhom3.project.modules.product.dto.response.VariantResponse;
import com.example.nhom3.project.modules.product.entity.Product;
import com.example.nhom3.project.modules.product.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "variants", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(target = "product", ignore = true)
    ProductVariant toVariant(VariantRequest request);

    VariantResponse toVariantResponse(ProductVariant variant);

    List<ProductResponse> toResponseList(List<Product> products);
}
