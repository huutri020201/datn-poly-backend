package com.example.nhom3.project.modules.cart.repository;

import com.example.nhom3.project.modules.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, UUID variantId);
    // Ép Hibernate xóa thẳng dưới Database và dọn dẹp bộ nhớ đệm
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.variantId = :variantId")
    void deleteByCartIdAndVariantId(@Param("cartId") Long cartId, @Param("variantId") UUID variantId);

    // Ép xóa cứng toàn bộ giỏ hàng
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);
}