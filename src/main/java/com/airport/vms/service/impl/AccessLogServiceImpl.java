package com.airport.vms.service.impl;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.domain.Visit;
import com.airport.vms.dto.AccessLogDto;
import com.airport.vms.mapper.AccessLogMapper;
import com.airport.vms.repository.AccessLogRepository;
import com.airport.vms.service.AccessLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;

    public AccessLogServiceImpl(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    @Override
    public void appendLog(AccessLog.EventType eventType, Visit visit, String actor, String details) {
        AccessLog accessLog = new AccessLog();
        accessLog.setTimestamp(LocalDateTime.now());
        accessLog.setEventType(eventType);
        accessLog.setVisit(visit);
        accessLog.setActor(actor);
        accessLog.setDetails(details);
        accessLog.setCreatedAt(LocalDateTime.now());
        accessLogRepository.save(accessLog);
    }

    @Override
    public Page<AccessLogDto.AccessLogResponse> queryLogs(Long visitId, AccessLog.EventType eventType, Pageable pageable) {
        if (visitId != null) {
            return accessLogRepository.findByVisitId(visitId, pageable).map(AccessLogMapper::toResponse);
        } else if (eventType != null) {
            // This is a simplified implementation. A real-world scenario would involve
            // more complex filtering logic, including date ranges.
            return accessLogRepository.findByEventTypeAndTimestampBetween(eventType, LocalDateTime.MIN, LocalDateTime.MAX, pageable)
                    .map(AccessLogMapper::toResponse);
        }
        return accessLogRepository.findAll(pageable).map(AccessLogMapper::toResponse);
    }
}
