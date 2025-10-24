package com.airport.vms.controller;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.dto.AccessLogDto;
import com.airport.vms.service.AccessLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('AUDITOR')")
    public ResponseEntity<Page<AccessLogDto.AccessLogResponse>> queryLogs(
            @RequestParam(required = false) Long visitId,
            @RequestParam(required = false) AccessLog.EventType eventType,
            Pageable pageable) {
        return ResponseEntity.ok(accessLogService.queryLogs(visitId, eventType, pageable));
    }
}
