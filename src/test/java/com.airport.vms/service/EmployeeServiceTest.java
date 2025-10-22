package com.airport.vms.service;

import com.airport.vms.domain.Employee;
import com.airport.vms.dto.EmployeeDto;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.service.impl.EmployeeServiceImpl;
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
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto.EmployeeRequest employeeRequest;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Test Employee");
        employee.setCreatedAt(LocalDateTime.now());

        employeeRequest = new EmployeeDto.EmployeeRequest("Test Employee", null, null, null, null, null);
    }

    @Test
    void whenCreateEmployee_thenReturnEmployeeResponse() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto.EmployeeResponse response = employeeService.createEmployee(employeeRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void whenGetEmployee_thenReturnEmployeeResponse() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDto.EmployeeResponse response = employeeService.getEmployee(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }
}
