package com.airport.vms.service;

import com.airport.vms.domain.AccessZone;
import com.airport.vms.dto.AccessZoneDto;
import com.airport.vms.repository.AccessZoneRepository;
import com.airport.vms.service.impl.AccessZoneServiceImpl;
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
public class AccessZoneServiceTest {

    @Mock
    private AccessZoneRepository accessZoneRepository;

    @InjectMocks
    private AccessZoneServiceImpl accessZoneService;

    private AccessZone accessZone;
    private AccessZoneDto.AccessZoneRequest accessZoneRequest;

    @BeforeEach
    void setUp() {
        accessZone = new AccessZone();
        accessZone.setId(1L);
        accessZone.setName("Terminal 1");
        accessZone.setCode("T1_SECURE");
        accessZone.setCreatedAt(LocalDateTime.now());

        accessZoneRequest = new AccessZoneDto.AccessZoneRequest("T1_SECURE", "Terminal 1", null, null);
    }

    @Test
    void whenCreateZone_thenReturnAccessZoneResponse() {
        when(accessZoneRepository.save(any(AccessZone.class))).thenReturn(accessZone);

        AccessZoneDto.AccessZoneResponse response = accessZoneService.createZone(accessZoneRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void whenGetZone_thenReturnAccessZoneResponse() {
        when(accessZoneRepository.findById(1L)).thenReturn(Optional.of(accessZone));

        AccessZoneDto.AccessZoneResponse response = accessZoneService.getZone(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }
}
