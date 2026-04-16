package com.example.nhom3.project.modules.identity.repository;

import com.example.nhom3.project.modules.identity.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {

    List<Ward> findByDistrictCode(String districtCode);
}
