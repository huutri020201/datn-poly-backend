package com.example.nhom3.project.modules.profile.dto;

public class ApiResponse<T> {

    private T data;
    private String message;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
    public static ApiResponse<String> success(String message) {
        return new ApiResponse<>(null, message);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

