package com.example.nhom3.project.modules.identity.service;

import com.example.nhom3.project.modules.identity.entity.District;
import com.example.nhom3.project.modules.identity.entity.Province;
import com.example.nhom3.project.modules.identity.entity.Ward;

import java.util.List;

public interface GeoService {
    List<Province> getProvinces();
    List<District> getDistricts(String provinceCode);
    List<Ward> getWards(String districtCode);
}
