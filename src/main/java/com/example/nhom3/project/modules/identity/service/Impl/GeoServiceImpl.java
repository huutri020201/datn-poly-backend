package com.example.nhom3.project.modules.identity.service.Impl;

import com.example.nhom3.project.modules.identity.entity.District;
import com.example.nhom3.project.modules.identity.entity.Province;
import com.example.nhom3.project.modules.identity.entity.Ward;
import com.example.nhom3.project.modules.identity.repository.DistrictRepository;
import com.example.nhom3.project.modules.identity.repository.ProvinceRepository;
import com.example.nhom3.project.modules.identity.repository.WardRepository;
import com.example.nhom3.project.modules.identity.service.GeoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeoServiceImpl implements GeoService {

    ProvinceRepository provinceRepo;
    DistrictRepository districtRepo;
    WardRepository wardRepo;

    @Override
    public List<Province> getProvinces() {
        return provinceRepo.findAll();
    }

    @Override
    public List<District> getDistricts(String provinceCode) {
        return districtRepo.findByProvinceCode(provinceCode);
    }

    @Override
    public List<Ward> getWards(String districtCode) {
        return wardRepo.findByDistrictCode(districtCode);
    }
}
