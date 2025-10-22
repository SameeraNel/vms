package com.airport.vms.controller;

import com.airport.vms.dto.VisitDto;
import com.airport.vms.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitDto.VisitResponse> preRegisterVisit(@Valid @RequestBody VisitDto.VisitCreateRequest visitCreateRequest) {
        return new ResponseEntity<>(visitService.preRegisterVisit(visitCreateRequest), HttpStatus.CREATED);
    }

    @PostMapping("/checkin")
    public ResponseEntity<VisitDto.VisitResponse> checkIn(@RequestBody VisitDto.CheckInRequest checkInRequest) {
        return ResponseEntity.ok(visitService.checkIn(checkInRequest.visitId(), checkInRequest.badgeNumber(), checkInRequest.kioskId(), checkInRequest.actor()));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<VisitDto.VisitResponse> checkInById(@PathVariable Long id, @RequestBody VisitDto.CheckInRequest checkInRequest) {
        return ResponseEntity.ok(visitService.checkIn(id, null, checkInRequest.kioskId(), checkInRequest.actor()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<VisitDto.VisitResponse> checkOut(@RequestBody VisitDto.CheckOutRequest checkOutRequest) {
        return ResponseEntity.ok(visitService.checkOut(checkOutRequest.visitId(), checkOutRequest.badgeNumber(), checkOutRequest.kioskId(), checkOutRequest.actor()));
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<VisitDto.VisitResponse> checkOutById(@PathVariable Long id, @RequestBody VisitDto.CheckOutRequest checkOutRequest) {
        return ResponseEntity.ok(visitService.checkOut(id, null, checkOutRequest.kioskId(), checkOutRequest.actor()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto.VisitResponse> getVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisit(id));
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<VisitDto.VisitResponse>> searchVisits(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String visitorName,
            @RequestParam(required = false) Long hostId,
            org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(visitService.searchVisits(status, visitorName, hostId, pageable));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelVisit(@PathVariable Long id, @RequestParam String reason) {
        visitService.cancelVisit(id, reason);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/badge")
    public ResponseEntity<com.airport.vms.dto.BadgeDto> getBadge(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getBadge(id));
    }

    @GetMapping(value = "/{id}/qr", produces = org.springframework.http.MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrCode(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getQrCode(id));
    }
}
