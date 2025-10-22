package com.airport.vms.controller;

import com.airport.vms.dto.VisitorDto;
import com.airport.vms.service.VisitorService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorController.class)
@Import(GlobalExceptionHandler.class)
public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateVisitor_withValidInput_thenReturns201() throws Exception {
        VisitorDto.VisitorRequest request = new VisitorDto.VisitorRequest("John", "Doe", null, "john.doe@example.com", null, null, null, null, null);
        VisitorDto.VisitorResponse response = new VisitorDto.VisitorResponse(1L, "John", "Doe", null, "john.doe@example.com", null, null, null, null, null, LocalDateTime.now(), null);

        when(visitorService.createVisitor(any(VisitorDto.VisitorRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/visitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenCreateVisitor_withInvalidInput_thenReturns400() throws Exception {
        VisitorDto.VisitorRequest request = new VisitorDto.VisitorRequest(null, "Doe", null, "john.doe@example.com", null, null, null, null, null);

        mockMvc.perform(post("/api/v1/visitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
