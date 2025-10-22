package com.airport.vms.controller;

import com.airport.vms.dto.EmployeeDto;
import com.airport.vms.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto.EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeDto.EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto.EmployeeResponse> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto.EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto.EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<EmployeeDto.EmployeeResponse>> listEmployees(org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(employeeService.listEmployees(pageable));
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncEmployees(@RequestBody List<EmployeeDto.EmployeeRequest> batch) {
        employeeService.syncEmployeesFromDirectory(batch);
        return ResponseEntity.ok().build();
    }
}
