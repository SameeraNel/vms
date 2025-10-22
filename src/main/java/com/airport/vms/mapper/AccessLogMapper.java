package com.airport.vms.mapper;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.dto.AccessLogDto;

public class AccessLogMapper {

    public static AccessLogDto.AccessLogResponse toResponse(AccessLog accessLog) {
        return new AccessLogDto.AccessLogResponse(
                accessLog.getId(),
                accessLog.getTimestamp(),
                accessLog.getEventType(),
                accessLog.getVisit() != null ? accessLog.getVisit().getId() : null,
                accessLog.getActor(),
                accessLog.getDetails()
        );
    }
}
