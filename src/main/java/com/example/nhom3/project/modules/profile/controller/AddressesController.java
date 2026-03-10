package com.example.nhom3.project.modules.profile.controller;


import com.example.nhom3.project.modules.profile.entity.Addresses;
import com.example.nhom3.project.modules.profile.repository.AddressesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressesController {
    private final AddressesRepository addressesRepository;

    // Lấy danh sách địa chỉ theo profile
    @GetMapping("/profile/{profileId}")
    public List<Addresses> getAddressesByProfile(@PathVariable UUID profileId) {
        return addressesRepository.findByProfileId(profileId);
    }

    // Lấy 1 địa chỉ
    @GetMapping("/{id}")
    public Addresses getAddressById(@PathVariable UUID id) {
        return addressesRepository.findById(id).orElse(null);
    }

    // Thêm địa chỉ
    @PostMapping
    public Addresses createAddress(@RequestBody Addresses address) {
        return addressesRepository.save(address);
    }

    // Cập nhật địa chỉ
    @PutMapping("/{id}")
    public Addresses updateAddress(@PathVariable UUID id, @RequestBody Addresses newAddress) {

        return addressesRepository.findById(id)
                .map(address -> {
                    address.setReceiverName(newAddress.getReceiverName());
                    address.setPhoneNumber(newAddress.getPhoneNumber());
                    address.setProvinceCode(newAddress.getProvinceCode());
                    address.setDistrictCode(newAddress.getDistrictCode());
                    address.setWardCode(newAddress.getWardCode());
                    address.setDetailAddress(newAddress.getDetailAddress());
                    address.setIsDefault(newAddress.getIsDefault());
                    return addressesRepository.save(address);
                }).orElse(null);
    }

    // Xóa địa chỉ
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        addressesRepository.deleteById(id);
    }
}

