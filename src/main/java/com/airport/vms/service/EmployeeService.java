package com.airport.vms.service;

import com.airport.vms.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto.EmployeeResponse createEmployee(EmployeeDto.EmployeeRequest employeeRequest);

    EmployeeDto.EmployeeResponse updateEmployee(Long id, EmployeeDto.EmployeeRequest employeeRequest);

    EmployeeDto.EmployeeResponse getEmployee(Long id);

    void syncEmployeesFromDirectory(java.util.List<EmployeeDto.EmployeeRequest> batch);
}
