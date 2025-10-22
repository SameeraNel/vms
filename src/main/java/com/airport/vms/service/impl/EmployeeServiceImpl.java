package com.airport.vms.service.impl;

import com.airport.vms.domain.Employee;
import com.airport.vms.dto.EmployeeDto;
import com.airport.vms.exception.ResourceNotFoundException;
import com.airport.vms.mapper.EmployeeMapper;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto.EmployeeResponse createEmployee(EmployeeDto.EmployeeRequest employeeRequest) {
        Employee employee = EmployeeMapper.toEntity(employeeRequest);
        employee.setCreatedAt(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Override
    public EmployeeDto.EmployeeResponse updateEmployee(Long id, EmployeeDto.EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setName(employeeRequest.name());
        employee.setBadgeId(employeeRequest.badgeId());
        employee.setDepartment(employeeRequest.department());
        employee.setEmail(employeeRequest.email());
        employee.setPhone(employeeRequest.phone());
        employee.setRole(employeeRequest.role());
        employee.setUpdatedAt(LocalDateTime.now());

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public EmployeeDto.EmployeeResponse getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public void syncEmployeesFromDirectory(java.util.List<EmployeeDto.EmployeeRequest> batch) {
        // This is a simplified implementation. A real-world scenario would involve
        // more complex logic to handle updates, deactivations, etc.
        for (EmployeeDto.EmployeeRequest employeeRequest : batch) {
            employeeRepository.findByBadgeId(employeeRequest.badgeId()).ifPresentOrElse(
                    employee -> {
                        // Update existing employee
                        employee.setName(employeeRequest.name());
                        employee.setDepartment(employeeRequest.department());
                        employee.setEmail(employeeRequest.email());
                        employee.setPhone(employeeRequest.phone());
                        employee.setRole(employeeRequest.role());
                        employee.setUpdatedAt(LocalDateTime.now());
                        employeeRepository.save(employee);
                    },
                    () -> {
                        // Create new employee
                        Employee newEmployee = EmployeeMapper.toEntity(employeeRequest);
                        newEmployee.setCreatedAt(LocalDateTime.now());
                        employeeRepository.save(newEmployee);
                    }
            );
        }
    }
}
