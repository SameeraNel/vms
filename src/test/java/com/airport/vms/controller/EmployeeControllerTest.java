package com.airport.vms.controller;

import com.airport.vms.dto.EmployeeDto;
import com.airport.vms.service.EmployeeService;
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

@WebMvcTest(EmployeeController.class)
@Import(GlobalExceptionHandler.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateEmployee_withValidInput_thenReturns201() throws Exception {
        EmployeeDto.EmployeeRequest request = new EmployeeDto.EmployeeRequest("Test Employee", null, null, null, null, null);
        EmployeeDto.EmployeeResponse response = new EmployeeDto.EmployeeResponse(1L, "Test Employee", null, null, null, null, null);

        when(employeeService.createEmployee(any(EmployeeDto.EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
