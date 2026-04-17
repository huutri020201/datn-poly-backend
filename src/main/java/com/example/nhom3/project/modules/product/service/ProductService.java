package com.example.nhom3.project.modules.product.service;

import com.example.nhom3.project.modules.product.dto.request.ProductRequest;
import com.example.nhom3.project.modules.product.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    // Tạo sản phẩm (Đã làm)
    ProductResponse createProduct(ProductRequest request, List<MultipartFile> images);

    // Lấy chi tiết sản phẩm qua Slug (Cho Client)
    ProductResponse getProductBySlug(String slug);

    // Lấy chi tiết sản phẩm qua ID (Cho Admin/Internal)
    ProductResponse getProductById(UUID id);
    List<ProductResponse> searchProducts(
            String keyword,
            UUID categoryId,
            UUID brandId,
            Double minPrice,
            Double maxPrice
    );
    List<ProductResponse> getAllProducts();

    // Cập nhật thông tin sản phẩm
    ProductResponse updateProduct(UUID id, ProductRequest request);

    // Xóa sản phẩm (Soft Delete)
    void deleteProduct(UUID id);
}
