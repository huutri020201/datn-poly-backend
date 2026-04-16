package com.example.nhom3.project.modules.identity.repository;

import com.example.nhom3.project.modules.identity.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
}
