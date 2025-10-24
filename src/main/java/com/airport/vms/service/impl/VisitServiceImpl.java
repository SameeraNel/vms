package com.airport.vms.service.impl;

import com.airport.vms.domain.*;
import com.airport.vms.dto.VisitDto;
import com.airport.vms.exception.InvalidVisitStatusException;
import com.airport.vms.exception.ResourceNotFoundException;
import com.airport.vms.mapper.VisitMapper;
import com.airport.vms.repository.AccessZoneRepository;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.repository.VisitRepository;
import com.airport.vms.repository.VisitorRepository;
import com.airport.vms.service.AccessLogService;
import com.airport.vms.service.VisitService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitorRepository visitorRepository;
    private final EmployeeRepository employeeRepository;
    private final AccessZoneRepository accessZoneRepository;
    private final AccessLogService accessLogService;

    public VisitServiceImpl(VisitRepository visitRepository, VisitorRepository visitorRepository,
                            EmployeeRepository employeeRepository, AccessZoneRepository accessZoneRepository,
                            AccessLogService accessLogService) {
        this.visitRepository = visitRepository;
        this.visitorRepository = visitorRepository;
        this.employeeRepository = employeeRepository;
        this.accessZoneRepository = accessZoneRepository;
        this.accessLogService = accessLogService;
    }

    @Override
    @Transactional
    public VisitDto.VisitResponse preRegisterVisit(VisitDto.VisitCreateRequest visitCreateRequest) {
        Visitor visitor;
        if (visitCreateRequest.visitorId() != null) {
            visitor = visitorRepository.findById(visitCreateRequest.visitorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + visitCreateRequest.visitorId()));
        } else if (visitCreateRequest.visitor() != null) {
            Visitor newVisitor = new Visitor();
            newVisitor.setFirstName(visitCreateRequest.visitor().firstName());
            newVisitor.setLastName(visitCreateRequest.visitor().lastName());
            newVisitor.setMiddleName(visitCreateRequest.visitor().middleName());
            newVisitor.setEmail(visitCreateRequest.visitor().email());
            newVisitor.setPhone(visitCreateRequest.visitor().phone());
            newVisitor.setOrganization(visitCreateRequest.visitor().organization());
            newVisitor.setIdDocumentType(visitCreateRequest.visitor().idDocumentType());
            newVisitor.setIdDocumentNumber(visitCreateRequest.visitor().idDocumentNumber());
            newVisitor.setPhotoUrl(visitCreateRequest.visitor().photoUrl());
            newVisitor.setCreatedAt(LocalDateTime.now());
            visitor = visitorRepository.save(newVisitor);
        } else {
            throw new IllegalArgumentException("Either visitorId or visitor object must be provided.");
        }

        Visit visit = VisitMapper.toEntity(visitCreateRequest);
        visit.setVisitor(visitor);
        visit.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit.setCreatedAt(LocalDateTime.now());
        visit.setBadgeNumber(UUID.randomUUID().toString()); // Generate a unique badge number

        if (visitCreateRequest.hostId() != null) {
            Employee host = employeeRepository.findById(visitCreateRequest.hostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Host not found with id: " + visitCreateRequest.hostId()));
            visit.setHost(host);
        }

        if (visitCreateRequest.permittedZoneIds() != null && !visitCreateRequest.permittedZoneIds().isEmpty()) {
            visit.setPermittedZones(new HashSet<>(accessZoneRepository.findAllById(visitCreateRequest.permittedZoneIds())));
        }

        Visit savedVisit = visitRepository.save(visit);
        accessLogService.appendLog(AccessLog.EventType.PRE_REGISTER, savedVisit, "SYSTEM", "Visit pre-registered.");

        return VisitMapper.toResponse(savedVisit);
    }

    @Override
    @Transactional
    public VisitDto.VisitResponse checkIn(Long visitId, String badgeNumber, String kioskId, String actor) {
        Visit visit = findVisit(visitId, badgeNumber);
        if (visit.getStatus() != Visit.VisitStatus.PRE_REGISTERED) {
            throw new InvalidVisitStatusException("Visit is not in PRE_REGISTERED state.");
        }
        visit.setStatus(Visit.VisitStatus.CHECKED_IN);
        visit.setCheckInTime(LocalDateTime.now());
        Visit updatedVisit = visitRepository.save(visit);
        accessLogService.appendLog(AccessLog.EventType.CHECK_IN, updatedVisit, actor != null ? actor : kioskId, "Visitor checked in.");
        return VisitMapper.toResponse(updatedVisit);
    }

    @Override
    @Transactional
    public VisitDto.VisitResponse checkOut(Long visitId, String badgeNumber, String kioskId, String actor) {
        Visit visit = findVisit(visitId, badgeNumber);
        if (visit.getStatus() != Visit.VisitStatus.CHECKED_IN) {
            throw new InvalidVisitStatusException("Visit is not in CHECKED_IN state.");
        }
        visit.setStatus(Visit.VisitStatus.CHECKED_OUT);
        visit.setCheckOutTime(LocalDateTime.now());
        visit.setActive(false);
        Visit updatedVisit = visitRepository.save(visit);
        accessLogService.appendLog(AccessLog.EventType.CHECK_OUT, updatedVisit, actor != null ? actor : kioskId, "Visitor checked out.");
        return VisitMapper.toResponse(updatedVisit);
    }

    @Override
    @Transactional
    public void cancelVisit(Long visitId, String reason) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        visit.setStatus(Visit.VisitStatus.CANCELLED);
        visitRepository.save(visit);
        accessLogService.appendLog(AccessLog.EventType.CANCEL, visit, "SYSTEM", "Visit cancelled. Reason: " + reason);
    }

    @Override
    public com.airport.vms.dto.BadgeDto getBadge(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        return new com.airport.vms.dto.BadgeDto(visit.getBadgeNumber(), "QR_PAYLOAD_PLACEHOLDER");
    }

    @Override
    public VisitDto.VisitResponse getVisit(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        return VisitMapper.toResponse(visit);
    }

    @Override
    public org.springframework.data.domain.Page<VisitDto.VisitResponse> searchVisits(String status, String visitorName, Long hostId, org.springframework.data.domain.Pageable pageable) {
        // This is a simplified implementation. A real-world scenario would use
        // Specifications or Querydsl for more complex filtering.
        if (status != null) {
            return visitRepository.findByStatus(Visit.VisitStatus.valueOf(status), pageable).map(VisitMapper::toResponse);
        }
        return visitRepository.findAll(pageable).map(VisitMapper::toResponse);
    }

    @Override
    public byte[] getQrCode(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(visit.getBadgeNumber(), BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Could not generate QR code", e);
        }
    }

    private Visit findVisit(Long visitId, String badgeNumber) {
        if (visitId != null) {
            return visitRepository.findById(visitId)
                    .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        } else if (badgeNumber != null) {
            return visitRepository.findByBadgeNumber(badgeNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Visit not found with badge number: " + badgeNumber));
        } else {
            throw new IllegalArgumentException("Either visitId or badgeNumber must be provided.");
        }
    }
}
