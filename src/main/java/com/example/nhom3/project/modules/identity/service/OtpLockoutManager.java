package com.example.nhom3.project.modules.identity.service;


import com.example.nhom3.project.modules.identity.entity.User;
import com.example.nhom3.project.modules.identity.entity.VerificationOtpCode;
import com.example.nhom3.project.modules.identity.repository.UserRepository;
import com.example.nhom3.project.modules.identity.repository.VerificationOtpCodeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OtpLockoutManager {
    VerificationOtpCodeRepository otpRepository;
    UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailedAttempt(VerificationOtpCode otp, User user) {
        otp.setAttemptCount(otp.getAttemptCount() + 1);
        otp.setLastAttemptAt(Instant.now());
        if (otp.getAttemptCount() >= 5) {
            otp.setUsed(true);
        }
        otpRepository.saveAndFlush(otp);
        user.setFailedAttemptCount(user.getFailedAttemptCount() + 1);
        if (user.getFailedAttemptCount() >= 10) {
            user.setLockedUntil(Instant.now().plus(Duration.ofHours(12)));
            user.setFailedAttemptCount(0);
        }
        userRepository.saveAndFlush(user);
    }
}
