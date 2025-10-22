package com.airport.vms.mapper;

import com.airport.vms.domain.AccessZone;
import com.airport.vms.dto.AccessZoneDto;

public class AccessZoneMapper {

    public static AccessZone toEntity(AccessZoneDto.AccessZoneRequest request) {
        AccessZone accessZone = new AccessZone();
        accessZone.setCode(request.code());
        accessZone.setName(request.name());
        accessZone.setDescription(request.description());
        accessZone.setLevel(request.level());
        return accessZone;
    }

    public static AccessZoneDto.AccessZoneResponse toResponse(AccessZone accessZone) {
        return new AccessZoneDto.AccessZoneResponse(
                accessZone.getId(),
                accessZone.getCode(),
                accessZone.getName(),
                accessZone.getDescription(),
                accessZone.getLevel()
        );
    }
}
