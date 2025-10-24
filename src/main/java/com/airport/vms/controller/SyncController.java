package com.airport.vms.controller;

import com.airport.vms.dto.SyncDto;
import com.airport.vms.service.SyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping("/visits-batch")
    public ResponseEntity<List<SyncDto.VisitSyncResponse>> pushVisitsBatch(@RequestBody List<SyncDto.VisitSyncRequest> batch, @RequestParam String kioskId) {
        return ResponseEntity.ok(syncService.pushVisitsBatch(batch, kioskId));
    }

    @GetMapping("/changes")
    public ResponseEntity<SyncDto.ChangesResponse> getChangesSince(@RequestParam LocalDateTime since) {
        return ResponseEntity.ok(syncService.getChangesSince(since));
    }
}
