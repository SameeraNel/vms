package com.airport.vms.repository;

import com.airport.vms.domain.AccessZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessZoneRepository extends JpaRepository<AccessZone, Long> {

    Optional<AccessZone> findByCode(String code);
}
