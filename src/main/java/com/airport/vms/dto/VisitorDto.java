package com.airport.vms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class VisitorDto {

    public record VisitorRequest(
            @NotNull @Size(max = 100) String firstName,
            @NotNull @Size(max = 100) String lastName,
            String middleName,
            @Email String email,
            String phone,
            String organization,
            String idDocumentType,
            String idDocumentNumber,
            String photoUrl
    ) {}

    public record VisitorResponse(
            Long id,
            String firstName,
            String lastName,
            String middleName,
            String email,
            String phone,
            String organization,
            String idDocumentType,
            String idDocumentNumber,
            String photoUrl,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
