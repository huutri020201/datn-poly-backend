package com.example.nhom3.project.modules.booking.repository;

import com.example.nhom3.project.modules.booking.entity.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, UUID> {

}