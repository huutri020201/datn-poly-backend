package com.example.nhom3.project.modules.profile.service;

import com.example.nhom3.project.modules.profile.entity.Addresses;


import java.util.List;
import java.util.UUID;

public interface AddressesService {
    List<Addresses> getAddressesByProfile(UUID profileId);
    Addresses getAddressById(UUID id);
    Addresses createAddress(Addresses address);
    Addresses updateAddress(UUID id, Addresses address);
    void deleteAddress(UUID id);
}
