package com.airport.vms.service.impl;

import com.airport.vms.domain.AccessZone;
import com.airport.vms.domain.Employee;
import com.airport.vms.domain.Visit;
import com.airport.vms.dto.SyncDto;
import com.airport.vms.mapper.AccessZoneMapper;
import com.airport.vms.mapper.EmployeeMapper;
import com.airport.vms.mapper.VisitMapper;
import com.airport.vms.repository.AccessZoneRepository;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.repository.VisitRepository;
import com.airport.vms.service.SyncService;
import com.airport.vms.service.VisitService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyncServiceImpl implements SyncService {

    private final VisitService visitService;
    private final VisitRepository visitRepository;
    private final EmployeeRepository employeeRepository;
    private final AccessZoneRepository accessZoneRepository;

    public SyncServiceImpl(VisitService visitService, VisitRepository visitRepository, EmployeeRepository employeeRepository, AccessZoneRepository accessZoneRepository) {
        this.visitService = visitService;
        this.visitRepository = visitRepository;
        this.employeeRepository = employeeRepository;
        this.accessZoneRepository = accessZoneRepository;
    }

    @Override
    public List<SyncDto.VisitSyncResponse> pushVisitsBatch(List<SyncDto.VisitSyncRequest> batch, String kioskId) {
        List<SyncDto.VisitSyncResponse> responses = new ArrayList<>();
        for (SyncDto.VisitSyncRequest request : batch) {
            try {
                // A real implementation would check the idempotency key to prevent duplicates
                visitService.preRegisterVisit(request.visit());
                responses.add(new SyncDto.VisitSyncResponse(request.localId(), null, "SUCCESS", null));
            } catch (Exception e) {
                responses.add(new SyncDto.VisitSyncResponse(request.localId(), null, "ERROR", e.getMessage()));
            }
        }
        return responses;
    }

    @Override
    public SyncDto.ChangesResponse getChangesSince(LocalDateTime timestamp) {
        List<Visit> visits = visitRepository.findAllByUpdatedAtAfter(timestamp);
        List<Employee> employees = employeeRepository.findAllByUpdatedAtAfter(timestamp);
        List<AccessZone> accessZones = accessZoneRepository.findAllByUpdatedAtAfter(timestamp);

        return new SyncDto.ChangesResponse(
                visits.stream().map(VisitMapper::toResponse).collect(Collectors.toList()),
                employees.stream().map(EmployeeMapper::toResponse).collect(Collectors.toList()),
                accessZones.stream().map(AccessZoneMapper::toResponse).collect(Collectors.toList()),
                LocalDateTime.now()
        );
    }
}
