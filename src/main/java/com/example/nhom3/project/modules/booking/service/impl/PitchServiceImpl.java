package com.example.nhom3.project.modules.booking.service.impl;

import com.example.nhom3.project.modules.booking.dto.response.PitchResponseDTO;
import com.example.nhom3.project.modules.booking.mapper.PitchMapper;
import com.example.nhom3.project.modules.booking.repository.PitchRepository;
import com.example.nhom3.project.modules.booking.service.PitchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PitchServiceImpl implements PitchService {

    private final PitchRepository pitchRepository;
    private final PitchMapper pitchMapper;

    @Override
    public List<PitchResponseDTO> getAllPitches() {
        return pitchRepository.findAll()
                .stream()
                .map(pitchMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}