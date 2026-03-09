package com.example.nhom3.project.modules.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/test-booking")
    public String showBookingPage() {
        // Trả về đúng tên file HTML nằm trong thư mục templates (KHÔNG CẦN ghi đuôi .html)
        return "booking-test";
    }
}