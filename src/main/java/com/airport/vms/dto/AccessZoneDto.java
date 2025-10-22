package com.airport.vms.dto;

import jakarta.validation.constraints.NotNull;

public class AccessZoneDto {

    public record AccessZoneRequest(
            @NotNull String code,
            @NotNull String name,
            String description,
            String level
    ) {}

    public record AccessZoneResponse(
            Long id,
            String code,
            String name,
            String description,
            String level
    ) {}
}
