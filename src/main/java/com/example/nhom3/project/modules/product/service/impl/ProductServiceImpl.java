package com.example.nhom3.project.modules.product.service.impl;

import com.example.nhom3.project.modules.inventory.service.InventoryService;
import com.example.nhom3.project.modules.product.dto.request.ProductRequest;
import com.example.nhom3.project.modules.product.dto.request.VariantRequest;
import com.example.nhom3.project.modules.product.dto.response.ProductResponse;
import com.example.nhom3.project.modules.product.entity.Brand;
import com.example.nhom3.project.modules.product.entity.Category;
import com.example.nhom3.project.modules.product.entity.Product;
import com.example.nhom3.project.modules.product.entity.ProductVariant;
import com.example.nhom3.project.modules.product.mapper.ProductMapper;
import com.example.nhom3.project.modules.product.repository.BrandRepository;
import com.example.nhom3.project.modules.product.repository.CategoryRepository;
import com.example.nhom3.project.modules.product.repository.ProductRepository;
import com.example.nhom3.project.modules.product.repository.ProductVariantRepository;
import com.example.nhom3.project.modules.product.service.ProductService;
import jakarta.persistence.EntityManager;
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
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ProductVariantRepository variantRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;
    InventoryService inventoryService;
    EntityManager entityManager;
    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("BRAND_NOT_FOUND"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("CATEGORY_NOT_FOUND"));

        // 2. Thu thập tất cả SKU từ request
        List<String> incomingSkus = request.getVariants().stream()
                .map(v -> v.getSku().trim())
                .toList();

        // 3. KIỂM TRA DUY NHẤT TRONG DB (Chỉ gọi 1 lần duy nhất)
        // Bạn có thể viết thêm hàm này trong Repository: List<ProductVariant> findBySkuIn(List<String> skus);
        for (String sku : incomingSkus) {
            if (variantRepository.existsBySku(sku)) {
                throw new RuntimeException("SKU_ALREADY_EXISTS_IN_DB: " + sku);
            }
        }

        // 4. LƯU PRODUCT CHA (Dùng saveAndFlush để có ID ngay lập tức)
        Product product = productMapper.toProduct(request);
        product.setBrand(brand);
        product.setCategory(category);

        final Product savedProduct = productRepository.saveAndFlush(product);

        // 5. LƯU TỪNG VARIANT
        for (VariantRequest variantReq : request.getVariants()) {
            ProductVariant variant = productMapper.toVariant(variantReq);
            variant.setProduct(savedProduct); // Dùng product đã được flush

            variant = variantRepository.save(variant);
            inventoryService.initializeStock(variant.getId(), variantReq.getStockQty());
        }
        variantRepository.flush(); // Đẩy hết variant xuống DB
        entityManager.refresh(product); // Nạp lại product kèm các variant đã có trong DB

        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("PRODUCT_NOT_FOUND"));
        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
       return productMapper.toResponseList(productRepository.findAll());
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PRODUCT_NOT_FOUND"));

        // Cập nhật thông tin cơ bản
        product.setName(request.getName());
        product.setBasePrice(request.getBasePrice());
        product.setDescription(request.getDescription());

        // Nếu thay đổi Brand hoặc Category
        if (!product.getBrand().getId().equals(request.getBrandId())) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("BRAND_NOT_FOUND"));
            product.setBrand(brand);
        }
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("CATEGORY_NOT_FOUND"));
            product.setCategory(category);
        }

        // Lưu ý: Cập nhật Variant rất phức tạp (thêm mới/xóa/sửa).
        // Trong đồ án đơn giản, bạn có thể chỉ cập nhật thông tin Product chính.

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        // Thay vì delete cứng, ta nên dùng Soft Delete nếu có field deleted_at
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("PRODUCT_NOT_FOUND");
        }
        productRepository.deleteById(id);
        log.info("Product with id {} has been deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new RuntimeException("PRODUCT_NOT_FOUND"));
    }
}
