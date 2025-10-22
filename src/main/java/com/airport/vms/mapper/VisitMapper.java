package com.airport.vms.mapper;

import com.airport.vms.domain.AccessZone;
import com.airport.vms.domain.Visit;
import com.airport.vms.dto.AccessZoneDto;
import com.airport.vms.dto.VisitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class VisitMapper {

    public static Visit toEntity(VisitDto.VisitCreateRequest request) {
        Visit visit = new Visit();
        visit.setPlannedArrival(request.plannedArrival() != null ? LocalDateTime.parse(request.plannedArrival(), DateTimeFormatter.ISO_DATE_TIME) : null);
        visit.setPlannedDeparture(request.plannedDeparture() != null ? LocalDateTime.parse(request.plannedDeparture(), DateTimeFormatter.ISO_DATE_TIME) : null);
        visit.setPurpose(request.purpose());
        visit.setSource(request.source());
        return visit;
    }

    public static VisitDto.VisitResponse toResponse(Visit visit) {
        return new VisitDto.VisitResponse(
                visit.getId(),
                VisitorMapper.toResponse(visit.getVisitor()),
                EmployeeMapper.toResponse(visit.getHost()),
                visit.getPlannedArrival(),
                visit.getPlannedDeparture(),
                visit.getCheckInTime(),
                visit.getCheckOutTime(),
                visit.getBadgeNumber(),
                visit.getStatus(),
                visit.getPermittedZones().stream()
                        .map(AccessZoneMapper::toResponse)
                        .collect(Collectors.toList()),
                visit.getCreatedAt(),
                visit.getUpdatedAt(),
                visit.getSource()
        );
    }
}
