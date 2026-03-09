package com.example.nhom3.project.modules.booking.repository;


import com.example.nhom3.project.modules.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);
    

    boolean existsByPitchIdAndStartTimeLessThanAndEndTimeGreaterThan(
            UUID pitchId, LocalDateTime endTime, LocalDateTime startTime);
}