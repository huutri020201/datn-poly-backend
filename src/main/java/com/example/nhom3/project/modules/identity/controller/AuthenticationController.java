package com.example.nhom3.project.modules.identity.controller;

import com.example.nhom3.project.modules.identity.dto.request.*;
import com.example.nhom3.project.modules.identity.dto.request.verify.ResendOtpRequest;
import com.example.nhom3.project.modules.identity.dto.request.verify.VerifyOtpRequest;
import com.example.nhom3.project.modules.identity.dto.request.verify.VerifyPhoneRequest;
import com.example.nhom3.project.modules.identity.dto.response.ApiResponse;
import com.example.nhom3.project.modules.identity.dto.response.AuthenticationResponse;
import com.example.nhom3.project.modules.identity.dto.response.IntrospectResponse;
import com.example.nhom3.project.modules.identity.dto.response.UserResponse;
import com.example.nhom3.project.modules.identity.enums.OtpType;
import com.example.nhom3.project.modules.identity.service.AuthenticationService;
import com.example.nhom3.project.modules.profile.dto.request.ProfileCompleteRequest;
import com.example.nhom3.project.modules.profile.dto.response.ProfileResponse;
import com.example.nhom3.project.modules.profile.service.ProfileService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    ProfileService profileService;

    @PostMapping("/register-email")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> registerByEmail(@RequestBody @Valid EmailRegisterRequest request) {
        authenticationService.registerByEmail(request);
        return ApiResponse.created(null, "Hãy kiểm tra email để xác thực tài khoản của bạn!");
    }

    @GetMapping("/verify-email/{token}")
    public ApiResponse<AuthenticationResponse> verifyEmail(@PathVariable String token) {
        return ApiResponse.success(authenticationService.verifyEmail(token), "Xác thực email thành công!");
    }

    @PostMapping("/complete-profile")
    public ApiResponse<ProfileResponse> completeProfile(@RequestBody @Valid ProfileCompleteRequest request) {
        UUID id = UUID.fromString(request.userId());
        return ApiResponse.success(
                profileService.completeInitialProfile(id, request),
                "Cập nhật hồ sơ thành công!"
        );
    }

    @PostMapping("/register-phone")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> registerByPhone(@RequestBody @Valid PhoneRegisterRequest request) {
        authenticationService.registerByPhone(request);
        return ApiResponse.created(null, "Hãy kiểm tra tin nhắn SMS để xác thực tài khoản của bạn!");
    }

    @PostMapping("/verify-phone")
    public ApiResponse<String> verifyPhone(@RequestBody @Valid VerifyPhoneRequest request) {
        authenticationService.verifyOtp(request.getPhone(), request.getOtpCode(), OtpType.REGISTER);
        return ApiResponse.success("Số điện thoại đã được xác thực thành công!");
    }

    @PostMapping("/resend-otp")
    public ApiResponse<String> resendOtp(@RequestBody @Valid ResendOtpRequest request) {
        authenticationService.resendOtp(request.getPhone(), OtpType.REGISTER);
        return ApiResponse.success("Mã OTP mới đã được gửi qua SMS!");
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ApiResponse.success(authenticationService.authenticate(request), "Login successfully");
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) {
        var result = authenticationService.refreshToken(request.getRefreshToken());
        return ApiResponse.success(result, "Token refreshed successfully");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.success(null, "Logout successfully");
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        return ApiResponse.success(authenticationService.introspect(request), "Introspect successfully");
    }

    @PostMapping("/find-account")
    public ApiResponse<UserResponse> findAccount(@RequestBody @Valid FindAccountRequest request) {
        return ApiResponse.success(authenticationService.findUserForReset(request.getIdentifier()), "Account found");
    }

    @PostMapping("/send-otp-reset")
    public ApiResponse<Void> sendOtpReset(@RequestBody @Valid SendOtpRequest request) {
        authenticationService.sendOtpForReset(request);
        return ApiResponse.success(null, "OTP sent");
    }

    @PostMapping("/verify-otp-reset")
    public ApiResponse<Void> verifyOtpReset(@RequestBody @Valid VerifyOtpRequest request) {
        authenticationService.verifyOtpForReset(request);
        return ApiResponse.success(null, "OTP valid");
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ApiResponse.success(null, "Password reset successfully");
    }
}


