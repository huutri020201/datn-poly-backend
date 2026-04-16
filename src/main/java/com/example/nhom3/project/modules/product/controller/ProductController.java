package com.example.nhom3.project.modules.product.controller;

import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.product.dto.request.ProductRequest;
import com.example.nhom3.project.modules.product.dto.response.ProductResponse;
import com.example.nhom3.project.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.created(productService.createProduct(request), "Tạo sản phẩm thành công");
    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ApiResponse.success(
                productService.searchProducts(keyword, categoryId, brandId, minPrice, maxPrice),
                "Lấy danh sách sản phẩm thành công"
        );
    }
    @GetMapping("/slug/{slug}")
    public ApiResponse<ProductResponse> getBySlug(@PathVariable String slug) {
        return ApiResponse.success(productService.getProductBySlug(slug), "Lấy chi tiết thành công");
    }

//    @GetMapping
//    public ApiResponse<List<ProductResponse>> getAll() {
//        return ApiResponse.success(productService.getAllProducts(),"Lấy thành công danh sách sản phẩm");
//    }
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(productService.getProductById(id),"Lấy chi tiết sản phẩm thành công");
    }
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequest request) {
        return ApiResponse.success(productService.updateProduct(id, request), "Cập nhật sản phẩm thành công");
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ApiResponse.success(null, "Đã xóa sản phẩm thành công");
    }
}