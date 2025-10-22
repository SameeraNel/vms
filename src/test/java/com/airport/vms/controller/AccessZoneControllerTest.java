package com.airport.vms.controller;

import com.airport.vms.dto.AccessZoneDto;
import com.airport.vms.service.AccessZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import com.airport.vms.controller.advice.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccessZoneController.class)
@Import(GlobalExceptionHandler.class)
public class AccessZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessZoneService accessZoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateZone_withValidInput_thenReturns201() throws Exception {
        AccessZoneDto.AccessZoneRequest request = new AccessZoneDto.AccessZoneRequest("T1_SECURE", "Terminal 1", null, null);
        AccessZoneDto.AccessZoneResponse response = new AccessZoneDto.AccessZoneResponse(1L, "T1_SECURE", "Terminal 1", null, null);

        when(accessZoneService.createZone(any(AccessZoneDto.AccessZoneRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/zones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
