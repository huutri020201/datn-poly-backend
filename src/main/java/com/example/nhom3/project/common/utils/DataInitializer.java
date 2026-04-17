package com.example.nhom3.project.common.utils;

import com.example.nhom3.project.modules.identity.service.GeoImportService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final GeoImportService geoImportService;

    @PostConstruct
    public void init() {
        geoImportService.importData();
    }
}