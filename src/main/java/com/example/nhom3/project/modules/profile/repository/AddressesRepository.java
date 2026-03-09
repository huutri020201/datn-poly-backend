package com.example.nhom3.project.modules.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nhom3.project.modules.profile.entity.Addresses;

import java.util.List;
import java.util.UUID;

public interface AddressesRepository extends JpaRepository<Addresses, UUID> {

    List<Addresses> findByProfileId(UUID profileId);
}
