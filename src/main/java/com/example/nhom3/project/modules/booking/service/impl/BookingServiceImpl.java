package com.example.nhom3.project.modules.booking.service.impl;

import com.example.nhom3.project.common.utils.EmailUtils;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final PitchRepository pitchRepository;
    private final UserRepository userRepository;
    private final EmailUtils emailUtils; //

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {

        // 1. Lấy thông tin User để lát nữa lấy Email
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin người dùng!"));

        // 2. Lấy thông tin Sân
        Pitch pitch = pitchRepository.findById(requestDTO.getPitchId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với mã ID này!"));

        // 3. Validate lịch trùng
        boolean isConflict = bookingRepository.existsByPitchIdAndStartTimeLessThanAndEndTimeGreaterThan(
                requestDTO.getPitchId(), requestDTO.getEndTime(), requestDTO.getStartTime());

        if (isConflict) {
            throw new RuntimeException("Sân đã được đặt trong khoảng thời gian này!");
        }

        Booking booking = bookingMapper.toEntity(requestDTO);

        // 4. Tính toán giá tiền
        BigDecimal pricePerHour = pitch.getPricePerHour();
        long minutes = Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        BigDecimal totalPrice = pricePerHour.multiply(BigDecimal.valueOf(minutes))
                .divide(BigDecimal.valueOf(60), 0, RoundingMode.HALF_UP);

        booking.setTotalPrice(totalPrice);
        booking.setStatus("PENDING");

        // 5. Lưu đơn đặt sân
        Booking savedBooking = bookingRepository.save(booking);
        BookingResponseDTO responseDTO = bookingMapper.toResponseDTO(savedBooking);

        // 6. GỌI HÀM GỬI EMAIL CHẠY NGẦM
        emailUtils.sendBookingConfirmationEmail(user.getEmail(), pitch.getName(), responseDTO);

        return responseDTO;
    }

    @Override
    public BookingResponseDTO getBookingById(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt sân"));
        return bookingMapper.toResponseDTO(booking);
    }

}