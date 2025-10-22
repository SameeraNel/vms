package com.airport.vms.mapper;

import com.airport.vms.domain.Employee;
import com.airport.vms.dto.EmployeeDto;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeDto.EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setBadgeId(request.badgeId());
        employee.setDepartment(request.department());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setRole(request.role());
        return employee;
    }

    public static EmployeeDto.EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDto.EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getBadgeId(),
                employee.getDepartment(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getRole()
        );
    }
}
