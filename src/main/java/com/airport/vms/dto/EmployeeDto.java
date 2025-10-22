package com.airport.vms.dto;

import jakarta.validation.constraints.NotNull;

public class EmployeeDto {

    public record EmployeeRequest(
            @NotNull String name,
            String badgeId,
            String department,
            String email,
            String phone,
            String role
    ) {}

    public record EmployeeResponse(
            Long id,
            String name,
            String badgeId,
            String department,
            String email,
            String phone,
            String role
    ) {}
}
