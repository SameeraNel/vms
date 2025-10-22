package com.airport.vms.controller;

import com.airport.vms.dto.VisitorDto;
import com.airport.vms.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    public ResponseEntity<VisitorDto.VisitorResponse> createVisitor(@Valid @RequestBody VisitorDto.VisitorRequest visitorRequest) {
        return new ResponseEntity<>(visitorService.createVisitor(visitorRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorDto.VisitorResponse> getVisitor(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getVisitor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitorDto.VisitorResponse> updateVisitor(@PathVariable Long id, @Valid @RequestBody VisitorDto.VisitorRequest visitorRequest) {
        return ResponseEntity.ok(visitorService.updateVisitor(id, visitorRequest));
    }

    @GetMapping
    public ResponseEntity<Page<VisitorDto.VisitorResponse>> searchVisitors(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(visitorService.searchVisitors(q, pageable));
    }
}
