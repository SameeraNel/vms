package com.airport.vms.service;

import com.airport.vms.dto.VisitDto;

public interface VisitService {

    VisitDto.VisitResponse preRegisterVisit(VisitDto.VisitCreateRequest visitCreateRequest);

    VisitDto.VisitResponse checkIn(Long visitId, String badgeNumber, String kioskId, String actor);

    VisitDto.VisitResponse checkOut(Long visitId, String badgeNumber, String kioskId, String actor);

    void cancelVisit(Long visitId, String reason);

    com.airport.vms.dto.BadgeDto getBadge(Long visitId);

    VisitDto.VisitResponse getVisit(Long id);

    org.springframework.data.domain.Page<VisitDto.VisitResponse> searchVisits(String status, String visitorName, Long hostId, org.springframework.data.domain.Pageable pageable);

    byte[] getQrCode(Long visitId);
}
