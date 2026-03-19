package com.example.nhom3.project.modules.booking.controller;

import com.example.nhom3.project.modules.booking.entity.Pitch;
import com.example.nhom3.project.modules.booking.repository.PitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pitches")
@RequiredArgsConstructor
public class PitchController {

    private final PitchRepository pitchRepository;

    @GetMapping
    public ResponseEntity<List<Pitch>> getAllPitches() {
        List<Pitch> pitches = pitchRepository.findAll();
        return ResponseEntity.ok(pitches);
    }
}