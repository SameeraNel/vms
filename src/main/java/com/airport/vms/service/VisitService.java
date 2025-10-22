package com.airport.vms.service;

import com.airport.vms.dto.VisitDto;

public interface VisitService {

    VisitDto.VisitResponse preRegisterVisit(VisitDto.VisitCreateRequest visitCreateRequest);

    VisitDto.VisitResponse checkIn(Long visitId, String badgeNumber, String kioskId, String actor);

    VisitDto.VisitResponse checkOut(Long visitId, String badgeNumber, String kioskId, String actor);

    void cancelVisit(Long visitId, String reason);

    // searchVisits will be implemented later
}
