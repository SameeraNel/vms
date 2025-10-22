package com.airport.vms.service;

import com.airport.vms.domain.Visitor;
import com.airport.vms.dto.VisitorDto;
import com.airport.vms.repository.VisitorRepository;
import com.airport.vms.service.impl.VisitorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private VisitorServiceImpl visitorService;

    private Visitor visitor;
    private VisitorDto.VisitorRequest visitorRequest;

    @BeforeEach
    void setUp() {
        visitor = new Visitor();
        visitor.setId(1L);
        visitor.setFirstName("John");
        visitor.setLastName("Doe");
        visitor.setCreatedAt(LocalDateTime.now());

        visitorRequest = new VisitorDto.VisitorRequest("John", "Doe", null, "john.doe@example.com", null, null, null, null, null);
    }

    @Test
    void whenCreateVisitor_thenReturnVisitorResponse() {
        when(visitorRepository.save(any(Visitor.class))).thenReturn(visitor);

        VisitorDto.VisitorResponse response = visitorService.createVisitor(visitorRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void whenGetVisitor_thenReturnVisitorResponse() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));

        VisitorDto.VisitorResponse response = visitorService.getVisitor(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }
}
