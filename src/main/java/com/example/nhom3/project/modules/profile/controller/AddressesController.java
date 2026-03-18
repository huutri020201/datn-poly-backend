package com.example.nhom3.project.modules.profile.controller;

import com.example.nhom3.project.modules.profile.dto.ApiResponse;
import com.example.nhom3.project.modules.profile.entity.Addresses;
import com.example.nhom3.project.modules.profile.service.AddressesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressesController {

    private final AddressesService addressesService;

    // ================= GET LIST =================
    @GetMapping("/profile/{profileId}")
    public ApiResponse<List<Addresses>> getAddressesByProfile(@PathVariable UUID profileId) {
        return ApiResponse.success(
                addressesService.getAddressesByProfile(profileId),
                "Lấy danh sách địa chỉ thành công"
        );
    }

    // ================= GET ONE =================
    @GetMapping("/{id}")
    public ApiResponse<Addresses> getAddressById(@PathVariable UUID id) {
        return ApiResponse.success(
                addressesService.getAddressById(id),
                "Lấy địa chỉ thành công"
        );
    }

    // ================= CREATE =================
    @PostMapping
    public ApiResponse<Addresses> createAddress(@RequestBody Addresses address) {
        return ApiResponse.success(
                addressesService.createAddress(address),
                "Tạo địa chỉ thành công"
        );
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ApiResponse<Addresses> updateAddress(
            @PathVariable UUID id,
            @RequestBody Addresses newAddress) {

        return ApiResponse.success(
                addressesService.updateAddress(id, newAddress),
                "Cập nhật địa chỉ thành công"
        );
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAddress(@PathVariable UUID id) {
        addressesService.deleteAddress(id);
        return ApiResponse.successMessage("Xóa địa chỉ thành công");
    }
}