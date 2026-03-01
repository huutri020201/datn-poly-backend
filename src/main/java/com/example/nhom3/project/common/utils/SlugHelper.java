package com.example.nhom3.project.common.utils;

import com.github.slugify.Slugify;


public class SlugHelper {
    private static final Slugify slg = Slugify.builder()
            .lowerCase(true)       // Chuyển về chữ thường
            .underscoreSeparator(true) // Thay khoảng trắng bằng dấu gạch ngang
            .build();

    public static String generate(String input) {
        if (input == null || input.isEmpty()) return null;
        return slg.slugify(input);
    }
}