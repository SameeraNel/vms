package com.airport.vms.dto;

import com.airport.vms.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public class VisitDto {

    public record VisitCreateRequest(
            Long visitorId,
            VisitorDto.VisitorRequest visitor,
            Long hostId,
            String plannedArrival,
            String plannedDeparture,
            String purpose,
            List<Long> permittedZoneIds,
            String source
    ) {}

    public record VisitResponse(
            Long id,
            VisitorDto.VisitorResponse visitor,
            EmployeeDto.EmployeeResponse host,
            LocalDateTime plannedArrival,
            LocalDateTime plannedDeparture,
            LocalDateTime checkInTime,
            LocalDateTime checkOutTime,
            String badgeNumber,
            Visit.VisitStatus status,
            List<AccessZoneDto.AccessZoneResponse> permittedZones,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String source
    ) {}

    public record CheckInRequest(
            Long visitId,
            String badgeNumber,
            String kioskId,
            String actor
    ) {}

    public record CheckOutRequest(
            Long visitId,
            String badgeNumber,
            String kioskId,
            String actor
    ) {}
}
