package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.modules.identity.dto.request.AddressRequest;
import com.example.nhom3.project.modules.identity.dto.response.AddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request);
    List<AddressResponse> getMyAddresses();
    AddressResponse updateAddress(UUID addressId, AddressRequest request);
    void deleteAddress(UUID addressId);
    AddressResponse setDefaultAddress(UUID addressId);
}
