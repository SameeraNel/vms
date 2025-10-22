package com.airport.vms.repository;

import com.airport.vms.domain.AccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    Page<AccessLog> findByVisitId(Long visitId, Pageable pageable);

    Page<AccessLog> findByEventTypeAndTimestampBetween(AccessLog.EventType eventType, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
