package com.example.nhom3.project.modules.identity.validator;


import com.example.nhom3.project.modules.identity.dto.request.EmailRegisterRequest;
import com.example.nhom3.project.modules.identity.dto.request.PhoneRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterValidator {
    public void validate(EmailRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("CONFIRM_PASSWORD_NOT_MATCH");
        }
    }

    public void validate(PhoneRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("CONFIRM_PASSWORD_NOT_MATCH");
        }
    }
}
