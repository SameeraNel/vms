package com.airport.vms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccessZoneDto {

    public record AccessZoneRequest(
            @NotNull @Pattern(regexp = "[A-Z0-9_]+") String code,
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
