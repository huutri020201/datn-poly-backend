package com.example.nhom3.project.modules.booking.service;

import com.example.nhom3.project.modules.booking.dto.response.PitchResponseDTO;
import java.util.List;

public interface PitchService {
    List<PitchResponseDTO> getAllPitches();
}