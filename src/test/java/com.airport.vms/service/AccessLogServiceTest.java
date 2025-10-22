package com.airport.vms.service;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.domain.Visit;
import com.airport.vms.repository.AccessLogRepository;
import com.airport.vms.service.impl.AccessLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccessLogServiceTest {

    @Mock
    private AccessLogRepository accessLogRepository;

    @InjectMocks
    private AccessLogServiceImpl accessLogService;

    @Test
    void whenAppendLog_thenSaveLog() {
        Visit visit = new Visit();
        visit.setId(1L);

        accessLogService.appendLog(AccessLog.EventType.CHECK_IN, visit, "kiosk123", "Details");

        verify(accessLogRepository).save(any(AccessLog.class));
    }
}
