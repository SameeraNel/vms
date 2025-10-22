package com.airport.vms.mapper;

import com.airport.vms.domain.Visitor;
import com.airport.vms.dto.VisitorDto;

public class VisitorMapper {

    public static Visitor toEntity(VisitorDto.VisitorRequest request) {
        Visitor visitor = new Visitor();
        visitor.setFirstName(request.firstName());
        visitor.setLastName(request.lastName());
        visitor.setMiddleName(request.middleName());
        visitor.setEmail(request.email());
        visitor.setPhone(request.phone());
        visitor.setOrganization(request.organization());
        visitor.setIdDocumentType(request.idDocumentType());
        visitor.setIdDocumentNumber(request.idDocumentNumber());
        visitor.setPhotoUrl(request.photoUrl());
        return visitor;
    }

    public static VisitorDto.VisitorResponse toResponse(Visitor visitor) {
        return new VisitorDto.VisitorResponse(
                visitor.getId(),
                visitor.getFirstName(),
                visitor.getLastName(),
                visitor.getMiddleName(),
                visitor.getEmail(),
                visitor.getPhone(),
                visitor.getOrganization(),
                visitor.getIdDocumentType(),
                visitor.getIdDocumentNumber(),
                visitor.getPhotoUrl(),
                visitor.getCreatedAt(),
                visitor.getUpdatedAt()
        );
    }
}
