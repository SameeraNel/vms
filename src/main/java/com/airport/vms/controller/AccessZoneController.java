package com.airport.vms.controller;

import com.airport.vms.dto.AccessZoneDto;
import com.airport.vms.service.AccessZoneService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/zones")
public class AccessZoneController {

    private final AccessZoneService accessZoneService;

    public AccessZoneController(AccessZoneService accessZoneService) {
        this.accessZoneService = accessZoneService;
    }

    @PostMapping
    public ResponseEntity<AccessZoneDto.AccessZoneResponse> createZone(@Valid @RequestBody AccessZoneDto.AccessZoneRequest accessZoneRequest) {
        return new ResponseEntity<>(accessZoneService.createZone(accessZoneRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessZoneDto.AccessZoneResponse> getZone(@PathVariable Long id) {
        return ResponseEntity.ok(accessZoneService.getZone(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessZoneDto.AccessZoneResponse> updateZone(@PathVariable Long id, @Valid @RequestBody AccessZoneDto.AccessZoneRequest accessZoneRequest) {
        return ResponseEntity.ok(accessZoneService.updateZone(id, accessZoneRequest));
    }

    @GetMapping
    public ResponseEntity<Page<AccessZoneDto.AccessZoneResponse>> listZones(Pageable pageable) {
        return ResponseEntity.ok(accessZoneService.listZones(pageable));
    }
}
