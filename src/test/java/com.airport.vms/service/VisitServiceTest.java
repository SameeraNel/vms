package com.airport.vms.service;

import com.airport.vms.domain.Employee;
import com.airport.vms.domain.Visit;
import com.airport.vms.domain.Visitor;
import com.airport.vms.dto.VisitDto;
import com.airport.vms.dto.VisitorDto;
import com.airport.vms.exception.InvalidVisitStatusException;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.repository.VisitRepository;
import com.airport.vms.repository.VisitorRepository;
import com.airport.vms.service.impl.VisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AccessLogService accessLogService;

    @InjectMocks
    private VisitServiceImpl visitService;

    private Visitor visitor;
    private Employee employee;
    private Visit visit;
    private VisitDto.VisitCreateRequest visitCreateRequest;

    @BeforeEach
    void setUp() {
        visitor = new Visitor();
        visitor.setId(1L);
        visitor.setFirstName("John");
        visitor.setLastName("Doe");

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Test Host");

        visit = new Visit();
        visit.setId(1L);
        visit.setVisitor(visitor);
        visit.setHost(employee);
        visit.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit.setCreatedAt(LocalDateTime.now());
        visit.setPermittedZones(Collections.emptySet());

        visitCreateRequest = new VisitDto.VisitCreateRequest(1L, null, 1L, null, null, null, null, null);
    }

    @Test
    void whenPreRegisterVisit_thenReturnVisitResponse() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        VisitDto.VisitResponse response = visitService.preRegisterVisit(visitCreateRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.status()).isEqualTo(Visit.VisitStatus.PRE_REGISTERED);
    }

    @Test
    void whenCheckIn_withValidStatus_thenCheckIn() {
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        VisitDto.VisitResponse response = visitService.checkIn(1L, null, "kiosk123", null);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(Visit.VisitStatus.CHECKED_IN);
    }

    @Test
    void whenCheckIn_withInvalidStatus_thenThrowException() {
        visit.setStatus(Visit.VisitStatus.CHECKED_IN);
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));

        assertThrows(InvalidVisitStatusException.class, () -> {
            visitService.checkIn(1L, null, "kiosk123", null);
        });
    }
}
