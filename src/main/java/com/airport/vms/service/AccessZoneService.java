package com.airport.vms.service;

import com.airport.vms.dto.AccessZoneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccessZoneService {

    AccessZoneDto.AccessZoneResponse createZone(AccessZoneDto.AccessZoneRequest accessZoneRequest);

    AccessZoneDto.AccessZoneResponse updateZone(Long id, AccessZoneDto.AccessZoneRequest accessZoneRequest);

    AccessZoneDto.AccessZoneResponse getZone(Long id);

    Page<AccessZoneDto.AccessZoneResponse> listZones(Pageable pageable);
}
