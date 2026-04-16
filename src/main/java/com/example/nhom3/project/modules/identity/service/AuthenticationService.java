package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.modules.identity.dto.request.*;
import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.identity.dto.response.AuthenticationResponse;
import com.example.nhom3.project.modules.identity.dto.response.IntrospectResponse;
import com.example.nhom3.project.modules.identity.dto.response.VerificationResponse;
import com.example.nhom3.project.modules.identity.enums.VerificationType;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    ApiResponse<Object> register(RegisterRequest request);
//    AuthenticationResponse verify(VerifyRequest request);
    VerificationResponse verify(VerifyRequest request);
    ApiResponse<Object> resendVerification(String identifier, VerificationType type);

    // --- LUỒNG ĐĂNG NHẬP & TOKEN ---
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refreshToken(RefreshTokenRequest request);
    IntrospectResponse introspect(IntrospectRequest request);
    void logout(LogoutRequest request) throws ParseException, JOSEException;

    // --- LUỒNG QUÊN MẬT KHẨU (FORGOT PASSWORD) ---
//    void forgotPassword(String identifier);
//    String verifyResetCode(VerifyRequest request);
//    void resetPassword(ResetPasswordRequest request);

    ApiResponse<Object> forgotPassword(String identifier);
    ApiResponse<Object> sendOtpForReset(String identifier, String method);
    void resetPassword(ResetPasswordRequest request);
}
