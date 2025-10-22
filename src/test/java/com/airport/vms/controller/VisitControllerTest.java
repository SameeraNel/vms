package com.airport.vms.controller;

import com.airport.vms.domain.Visit;
import com.airport.vms.dto.VisitDto;
import com.airport.vms.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import com.airport.vms.controller.advice.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
@Import(GlobalExceptionHandler.class)
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitService visitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenPreRegisterVisit_withValidInput_thenReturns201() throws Exception {
        VisitDto.VisitCreateRequest request = new VisitDto.VisitCreateRequest(1L, null, 1L, null, null, null, null, null);
        VisitDto.VisitResponse response = new VisitDto.VisitResponse(1L, null, null, null, null, null, null, "BADGE123", Visit.VisitStatus.PRE_REGISTERED, Collections.emptyList(), LocalDateTime.now(), null, null);

        when(visitService.preRegisterVisit(any(VisitDto.VisitCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
