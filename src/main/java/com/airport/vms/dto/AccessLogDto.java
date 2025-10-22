package com.airport.vms.dto;

import com.airport.vms.domain.AccessLog;

import java.time.LocalDateTime;

public class AccessLogDto {

    public record AccessLogResponse(
            Long id,
            LocalDateTime timestamp,
            AccessLog.EventType eventType,
            Long visitId,
            String actor,
            String details
    ) {}
}
