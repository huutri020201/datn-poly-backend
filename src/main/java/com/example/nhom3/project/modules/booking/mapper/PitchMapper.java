package com.example.nhom3.project.modules.booking.mapper;

import com.example.nhom3.project.modules.booking.dto.response.PitchResponseDTO;
import com.example.nhom3.project.modules.booking.entity.Pitch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PitchMapper {
    PitchResponseDTO toResponseDTO(Pitch pitch);
}