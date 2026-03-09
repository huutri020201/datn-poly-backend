package com.example.nhom3.project.modules.profile.service.Impl;

import com.example.nhom3.project.modules.profile.service.AddressesService;
import com.example.nhom3.project.modules.profile.repository.AddressesRepository;
import com.example.nhom3.project.modules.profile.entity.Addresses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressesServiceImpl implements AddressesService {
    private final AddressesRepository addressRepository;

    @Override
    public List<Addresses> getAddressesByProfile(UUID profileId) {
        return addressRepository.findByProfileId(profileId);
    }

    @Override
    public Addresses getAddressById(UUID id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Addresses createAddress(Addresses address) {
        return addressRepository.save(address);
    }

    @Override
    public Addresses updateAddress(UUID id, Addresses newAddress) {

        return addressRepository.findById(id)
                .map(address -> {
                    address.setReceiverName(newAddress.getReceiverName());
                    address.setPhoneNumber(newAddress.getPhoneNumber());
                    address.setProvinceCode(newAddress.getProvinceCode());
                    address.setDistrictCode(newAddress.getDistrictCode());
                    address.setWardCode(newAddress.getWardCode());
                    address.setDetailAddress(newAddress.getDetailAddress());
                    address.setIsDefault(newAddress.getIsDefault());
                    return addressRepository.save(address);
                }).orElse(null);
    }

    @Override
    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }
}