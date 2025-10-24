package com.airport.vms.controller;

import com.airport.vms.dto.VisitorDto;
import com.airport.vms.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visitors")
@Tag(name = "Visitors", description = "Operations related to visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    @Operation(summary = "Create a new visitor")
    public ResponseEntity<VisitorDto.VisitorResponse> createVisitor(@Valid @RequestBody VisitorDto.VisitorRequest visitorRequest) {
        return new ResponseEntity<>(visitorService.createVisitor(visitorRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a visitor by ID")
    public ResponseEntity<VisitorDto.VisitorResponse> getVisitor(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getVisitor(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing visitor")
    public ResponseEntity<VisitorDto.VisitorResponse> updateVisitor(@PathVariable Long id, @Valid @RequestBody VisitorDto.VisitorRequest visitorRequest) {
        return ResponseEntity.ok(visitorService.updateVisitor(id, visitorRequest));
    }

    @GetMapping
    @Operation(summary = "Search for visitors")
    public ResponseEntity<Page<VisitorDto.VisitorResponse>> searchVisitors(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(visitorService.searchVisitors(q, pageable));
    }
}
