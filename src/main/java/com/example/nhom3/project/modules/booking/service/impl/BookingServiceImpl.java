package com.example.nhom3.project.modules.booking.service.impl;

import com.example.nhom3.project.common.utils.EmailUtils;
import com.example.nhom3.project.common.utils.SecurityUtils;
import com.example.nhom3.project.modules.booking.dto.BookingRequestDTO;
import com.example.nhom3.project.modules.booking.dto.response.BookingResponseDTO;
import com.example.nhom3.project.modules.booking.entity.Booking;
import com.example.nhom3.project.modules.booking.entity.Pitch;
import com.example.nhom3.project.modules.booking.mapper.BookingMapper;
import com.example.nhom3.project.modules.booking.repository.BookingRepository;
import com.example.nhom3.project.modules.booking.repository.PitchRepository;
import com.example.nhom3.project.modules.booking.service.BookingService;
import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import com.example.nhom3.project.modules.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final PitchRepository pitchRepository;
    private final UserRepository userRepository;
    private final EmailUtils emailUtils;
    private final PromotionService promotionService;


    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {

        // Lấy thông tin User để lát nữa lấy Email
        User user = userRepository.findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin người dùng!"));

        // Lấy thông tin Sân
        Pitch pitch = pitchRepository.findById(requestDTO.getPitchId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với mã ID này!"));

        LocalDateTime startDateTime = LocalDateTime.of(requestDTO.getDate(), requestDTO.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(requestDTO.getDate(), requestDTO.getEndTime());

        if (endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)) {
            throw new RuntimeException("Giờ kết thúc phải lớn hơn giờ bắt đầu!");
        }

        boolean isConflict = bookingRepository.existsByPitchIdAndStartTimeLessThanAndEndTimeGreaterThan(
                requestDTO.getPitchId(), endDateTime, startDateTime);

        if (isConflict) {
            throw new RuntimeException("Sân đã được đặt trong khoảng thời gian này!");
        }

        Booking booking = bookingMapper.toEntity(requestDTO);
        booking.setStartTime(startDateTime);
        booking.setEndTime(endDateTime);
        booking.setUserId(user.getId());

        BigDecimal pricePerHour = pitch.getPricePerHour();
        long minutes = Duration.between(startDateTime, endDateTime).toMinutes();
        BigDecimal totalPrice = pricePerHour.multiply(BigDecimal.valueOf(minutes))
                .divide(BigDecimal.valueOf(60), 0, RoundingMode.HALF_UP);

        booking.setTotalPrice(totalPrice);
        booking.setStatus("PENDING");

        Booking savedBooking = bookingRepository.save(booking);
        BookingResponseDTO responseDTO = bookingMapper.toResponseDTO(savedBooking);
        // Voucher đặt sân
        promotionService.rewardVipVoucher(user, savedBooking.getTotalPrice());

        emailUtils.sendBookingConfirmationEmail(user.getEmail(), pitch.getName(), responseDTO);

        return responseDTO;
    }

    @Override
    public BookingResponseDTO getBookingById(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt sân"));
        return bookingMapper.toResponseDTO(booking);
    }
    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBookingStatus(UUID id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt sân"));

        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(updatedBooking);
    }

    @Override
    @Transactional
    public void deleteBooking(UUID id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đơn đặt sân để xóa");
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingResponseDTO> getMyBookings() {
        // Lấy ID người dùng đang đăng nhập
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        // Truy vấn DB và map sang DTO
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .toList();
    }
}