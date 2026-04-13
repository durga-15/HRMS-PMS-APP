package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.EmploymentTypeDto;
import com.hrms.pms.pms_app.pms.services.EmploymentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employment-types")
@RequiredArgsConstructor
public class EmploymentTypeController {

    private final EmploymentTypeService service;

    @PostMapping
    public ResponseEntity<EmploymentTypeDto> create(@Valid @RequestBody EmploymentTypeDto dto) {
        try {
            return new ResponseEntity<>(service.createEmploymentType(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmploymentTypeDto>> getAll() {
        return ResponseEntity.ok(service.getAllEmploymentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmploymentTypeDto> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.getEmploymentTypeById(id));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploymentTypeDto> update(@PathVariable UUID id,
                                                    @Valid @RequestBody EmploymentTypeDto dto) {
        try {
            return ResponseEntity.ok(service.updateEmploymentType(id, dto));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.deleteEmploymentType(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}