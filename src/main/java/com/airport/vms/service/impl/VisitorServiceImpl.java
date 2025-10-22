package com.airport.vms.service.impl;

import com.airport.vms.domain.Visitor;
import com.airport.vms.dto.VisitorDto;
import com.airport.vms.exception.ResourceNotFoundException;
import com.airport.vms.mapper.VisitorMapper;
import com.airport.vms.repository.VisitorRepository;
import com.airport.vms.service.VisitorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public VisitorDto.VisitorResponse createVisitor(VisitorDto.VisitorRequest visitorRequest) {
        Visitor visitor = VisitorMapper.toEntity(visitorRequest);
        visitor.setCreatedAt(LocalDateTime.now());
        Visitor savedVisitor = visitorRepository.save(visitor);
        return VisitorMapper.toResponse(savedVisitor);
    }

    @Override
    public VisitorDto.VisitorResponse updateVisitor(Long id, VisitorDto.VisitorRequest visitorRequest) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + id));

        visitor.setFirstName(visitorRequest.firstName());
        visitor.setLastName(visitorRequest.lastName());
        visitor.setMiddleName(visitorRequest.middleName());
        visitor.setEmail(visitorRequest.email());
        visitor.setPhone(visitorRequest.phone());
        visitor.setOrganization(visitorRequest.organization());
        visitor.setIdDocumentType(visitorRequest.idDocumentType());
        visitor.setIdDocumentNumber(visitorRequest.idDocumentNumber());
        visitor.setPhotoUrl(visitorRequest.photoUrl());
        visitor.setUpdatedAt(LocalDateTime.now());

        Visitor updatedVisitor = visitorRepository.save(visitor);
        return VisitorMapper.toResponse(updatedVisitor);
    }

    @Override
    public VisitorDto.VisitorResponse getVisitor(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + id));
        return VisitorMapper.toResponse(visitor);
    }

    @Override
    public Page<VisitorDto.VisitorResponse> searchVisitors(String query, Pageable pageable) {
        Page<Visitor> visitors = visitorRepository.searchByNameOrOrganization(query, pageable);
        return visitors.map(VisitorMapper::toResponse);
    }
}
