package com.example.nhom3.project.modules.order.service.impl;

import com.example.nhom3.project.modules.cart.entity.Cart;
import com.example.nhom3.project.modules.cart.entity.CartStatus;
import com.example.nhom3.project.modules.cart.repository.CartRepository;
import com.example.nhom3.project.modules.inventory.service.InventoryService;
import com.example.nhom3.project.modules.order.dto.request.OrderPlaceRequest;
import com.example.nhom3.project.modules.order.dto.response.OrderItemResponse;
import com.example.nhom3.project.modules.order.dto.response.OrderResponse;
import com.example.nhom3.project.modules.order.entity.Order;
import com.example.nhom3.project.modules.order.entity.OrderItem;
import com.example.nhom3.project.modules.order.entity.OrderStatus;
import com.example.nhom3.project.modules.order.mapper.OrderMapper;
import com.example.nhom3.project.modules.order.repository.OrderRepository;
import com.example.nhom3.project.modules.order.service.OrderService;
import com.example.nhom3.project.modules.payment.service.VNPAYService;
import com.example.nhom3.project.modules.product.dto.response.PageResponse;
import com.example.nhom3.project.modules.product.entity.ProductVariant;
import com.example.nhom3.project.modules.product.repository.ProductVariantRepository;
import com.example.nhom3.project.modules.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final CartRepository cartRepository;
    final ProductVariantRepository variantRepository;
    final InventoryService inventoryService;
    final OrderMapper orderMapper;
    final PromotionService promotionService;
    final VNPAYService vnpayService;
    @Override
    public OrderResponse placeOrder(UUID userId, OrderPlaceRequest request) {
        // 1. Tìm giỏ hàng đang hoạt động
        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng của bạn đang trống!"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Không có sản phẩm nào để thanh toán.");
        }

        // 2. Khởi tạo đối tượng Order (Chưa có tiền nong)
        Order order = Order.builder()
                .userId(userId)
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .shippingAddress(request.getShippingAddress())
                .note(request.getNote())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("UNPAID")
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 3. Chuyển CartItem sang OrderItem & Trừ kho từng món
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            ProductVariant variant = variantRepository.findById(cartItem.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + cartItem.getVariantId()));

            // TRỪ KHO: Nếu hết hàng, hàm này trong InventoryService sẽ ném Exception -> Rollback toàn bộ đơn
            inventoryService.deductStock(variant.getId(), cartItem.getQuantity());

            return OrderItem.builder()
                    .order(order)
                    .variantId(variant.getId())
                    .productId(variant.getProduct().getId())
                    .productName(variant.getProduct().getName())
                    .sku(variant.getSku())
                    .priceAtPurchase(variant.getPriceOverride() != null ?
                            variant.getPriceOverride() : variant.getProduct().getBasePrice())
                    .quantity(cartItem.getQuantity())
                    .imageSnapshot(variant.getImageUrl())
                    .attributesSnapshot(variant.getAttributes()) // Snapshot thuộc tính màu/size
                    .build();
        }).toList();

        // 4. Tính toán tài chính
        BigDecimal subTotal = orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = BigDecimal.ZERO;

        // Xử lý Voucher nếu có
        if (request.getVoucherCode() != null && !request.getVoucherCode().isBlank()) {
            // Gọi PromotionService bạn vừa bổ sung hàm validateAndCalculateDiscount
            discountAmount = promotionService.validateAndCalculateDiscount(userId, request.getVoucherCode(), subTotal);
            order.setVoucherCode(request.getVoucherCode());
        }

        order.setItems(orderItems);
        order.setSubTotal(subTotal);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(subTotal.subtract(discountAmount));

        // 5. Lưu đơn hàng vào Database
        Order savedOrder = orderRepository.save(order);

        // 6. Cập nhật trạng thái Voucher thành "USED" (Nếu có dùng)
        if (order.getVoucherCode() != null) {
            promotionService.useVoucher(userId, order.getVoucherCode(), savedOrder.getId());
        }

        // 7. Vô hiệu hóa giỏ hàng cũ
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        log.info("Đơn hàng mới tạo thành công, giỏ hàng của user {} đã được dọn trống", userId);
        // 8. BỔ SUNG: Tạo Payment URL nếu chọn VNPAY
        OrderResponse response = orderMapper.toResponse(savedOrder);
        if ("VNPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            // Giả định bạn truyền IP từ Controller qua Request hoặc lấy mặc định
            String ipAddress = "127.0.0.1";
            String paymentUrl = vnpayService.createPaymentUrl(savedOrder, ipAddress);
            response.setPaymentUrl(paymentUrl);
            log.info("Đã tạo link thanh toán VNPAY cho đơn hàng: {}", savedOrder.getId());
        }

        log.info("Đơn hàng mới tạo thành công: ID={}, User={}", savedOrder.getId(), userId);
        return response;
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(UUID orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"));

        // Kiểm tra nếu đơn hàng chuyển từ trạng thái khác sang CANCELLED
        if (newStatus == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.CANCELLED) {

            // 1. HOÀN KHO (Sản phẩm quay lại kho)
            for (OrderItem item : order.getItems()) {
                inventoryService.addStock(item.getVariantId(), item.getQuantity());
            }

            // 2. HOÀN VOUCHER (Nếu đơn hàng có dùng mã giảm giá)
            if (order.getVoucherCode() != null && !order.getVoucherCode().isBlank()) {
                promotionService.refundVoucher(order.getUserId(), order.getVoucherCode());
            }

            log.info("Đơn hàng {} đã được hủy. Kho và Voucher đã được hoàn lại.", orderId);
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(UUID userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderDetail(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getAllOrders(int page, int size, String status) {
        // page - 1 vì Spring Data Pageable bắt đầu từ 0
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Order> orderPage;
        if (status != null && !status.isEmpty()) {
            orderPage = orderRepository.findAllByStatusOrderByCreatedAtDesc(OrderStatus.valueOf(status), pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        return PageResponse.<OrderResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .data(orderPage.getContent().stream()
                        .map(orderMapper::toResponse)
                        .toList())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Order getEntityById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("KHÔNG TÌM THẤY ĐƠN HÀNG: " + orderId));
    }

    @Override
    @Transactional
    public void completePayment(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("KHÔNG TÌM THẤY ĐƠN HÀNG"));

        // Chỉ cập nhật nếu đơn chưa thanh toán
        if (!"PAID".equals(order.getPaymentStatus())) {
            order.setPaymentStatus("PAID");
            order.setStatus(OrderStatus.CONFIRMED); // Tự động xác nhận đơn khi có tiền
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
            log.info("XÁC NHẬN THANH TOÁN THÀNH CÔNG: Đơn hàng {}", orderId);
        }
    }

    @Override
    @Transactional
    public void failPayment(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("KHÔNG TÌM THẤY ĐƠN HÀNG"));

        // Có thể giữ nguyên trạng thái PENDING để khách thanh toán lại
        // Hoặc ghi log/thông báo
        log.warn("THANH TOÁN THẤT BẠI: Đơn hàng {}", orderId);
    }

}
