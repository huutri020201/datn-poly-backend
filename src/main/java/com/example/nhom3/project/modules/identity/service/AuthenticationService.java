package com.example.nhom3.project.modules.identity.service;


import com.example.nhom3.project.modules.identity.dto.request.*;
import com.example.nhom3.project.modules.identity.dto.request.verify.VerifyOtpRequest;
import com.example.nhom3.project.modules.identity.dto.response.AuthenticationResponse;
import com.example.nhom3.project.modules.identity.dto.response.IntrospectResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.enums.OtpType;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    void registerByEmail(EmailRegisterRequest request);

    AuthenticationResponse verifyEmail(String token);

    void registerByPhone(PhoneRegisterRequest request);

    void verifyOtp(String identifier, String otpCode, OtpType type);

    void resendOtp(String identifier, OtpType type);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String requestRefreshToken);

    IntrospectResponse introspect(IntrospectRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    UserResponse findUserForReset(String identifier);

    void sendOtpForReset(SendOtpRequest request);

    void verifyOtpForReset(VerifyOtpRequest request);

    void resetPassword(ResetPasswordRequest request);

//    void cleanupUnverifiedUsers();
}
