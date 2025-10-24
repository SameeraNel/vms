package com.airport.vms.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SyncDto {

    public record VisitSyncRequest(
            String localId,
            String idempotencyKey,
            VisitDto.VisitCreateRequest visit
    ) {}

    public record VisitSyncResponse(
            String localId,
            Long serverId,
            String status,
            String errorMessage
    ) {}

    public record ChangesResponse(
            List<VisitDto.VisitResponse> visits,
            List<EmployeeDto.EmployeeResponse> employees,
            List<AccessZoneDto.AccessZoneResponse> accessZones,
            LocalDateTime timestamp
    ) {}
}
