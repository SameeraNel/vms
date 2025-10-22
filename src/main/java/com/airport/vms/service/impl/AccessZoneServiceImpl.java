package com.airport.vms.service.impl;

import com.airport.vms.domain.AccessZone;
import com.airport.vms.dto.AccessZoneDto;
import com.airport.vms.exception.ResourceNotFoundException;
import com.airport.vms.mapper.AccessZoneMapper;
import com.airport.vms.repository.AccessZoneRepository;
import com.airport.vms.service.AccessZoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessZoneServiceImpl implements AccessZoneService {

    private final AccessZoneRepository accessZoneRepository;

    public AccessZoneServiceImpl(AccessZoneRepository accessZoneRepository) {
        this.accessZoneRepository = accessZoneRepository;
    }

    @Override
    public AccessZoneDto.AccessZoneResponse createZone(AccessZoneDto.AccessZoneRequest accessZoneRequest) {
        AccessZone accessZone = AccessZoneMapper.toEntity(accessZoneRequest);
        accessZone.setCreatedAt(LocalDateTime.now());
        AccessZone savedAccessZone = accessZoneRepository.save(accessZone);
        return AccessZoneMapper.toResponse(savedAccessZone);
    }

    @Override
    public AccessZoneDto.AccessZoneResponse updateZone(Long id, AccessZoneDto.AccessZoneRequest accessZoneRequest) {
        AccessZone accessZone = accessZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccessZone not found with id: " + id));

        accessZone.setCode(accessZoneRequest.code());
        accessZone.setName(accessZoneRequest.name());
        accessZone.setDescription(accessZoneRequest.description());
        accessZone.setLevel(accessZoneRequest.level());
        accessZone.setUpdatedAt(LocalDateTime.now());

        AccessZone updatedAccessZone = accessZoneRepository.save(accessZone);
        return AccessZoneMapper.toResponse(updatedAccessZone);
    }

    @Override
    public AccessZoneDto.AccessZoneResponse getZone(Long id) {
        AccessZone accessZone = accessZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccessZone not found with id: " + id));
        return AccessZoneMapper.toResponse(accessZone);
    }

    @Override
    public Page<AccessZoneDto.AccessZoneResponse> listZones(Pageable pageable) {
        Page<AccessZone> accessZones = accessZoneRepository.findAll(pageable);
        return accessZones.map(AccessZoneMapper::toResponse);
    }
}
