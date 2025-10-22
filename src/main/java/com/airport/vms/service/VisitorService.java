package com.airport.vms.service;

import com.airport.vms.dto.VisitorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitorService {

    VisitorDto.VisitorResponse createVisitor(VisitorDto.VisitorRequest visitorRequest);

    VisitorDto.VisitorResponse updateVisitor(Long id, VisitorDto.VisitorRequest visitorRequest);

    VisitorDto.VisitorResponse getVisitor(Long id);

    Page<VisitorDto.VisitorResponse> searchVisitors(String query, Pageable pageable);
}
