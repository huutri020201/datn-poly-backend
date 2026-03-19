package com.example.nhom3.project.modules.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, 200, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "Success", data);
    }

    public static ApiResponse<?> successMessage(String message) {
        return new ApiResponse<>(true, 200, message, null);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(false, 400, message, null);
    }
    public static ApiResponse<?> error(int code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}