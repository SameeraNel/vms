package com.airport.vms.service;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.domain.Visit;
import com.airport.vms.dto.AccessLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccessLogService {

    void appendLog(AccessLog.EventType eventType, Visit visit, String actor, String details);

    Page<AccessLogDto.AccessLogResponse> queryLogs(Long visitId, AccessLog.EventType eventType, Pageable pageable);
}
