package com.airport.vms.service;

import com.airport.vms.dto.SyncDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncService {

    List<SyncDto.VisitSyncResponse> pushVisitsBatch(List<SyncDto.VisitSyncRequest> batch, String kioskId);

    SyncDto.ChangesResponse getChangesSince(LocalDateTime timestamp);
}
