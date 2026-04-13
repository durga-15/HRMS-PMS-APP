package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.SalaryComponentDto;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentResponseDto;
import com.hrms.pms.pms_app.pms.services.SalaryComponentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salary-components")
public class SalaryComponentController {

    private final SalaryComponentService service;

    public SalaryComponentController(SalaryComponentService service) {
        this.service = service;
    }

    //Create a new Salary Component
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SalaryComponentResponseDto> createSalaryComponent(
            @Valid @RequestBody SalaryComponentDto dto) {

        SalaryComponentResponseDto response = service.createSalaryComponent(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //Get all salary components
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SalaryComponentDto>> getAllSalaryComponents() {
        List<SalaryComponentDto> components = service.getAllSalaryComponents();
        return ResponseEntity.ok(components);
    }

//    Get salary component by ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaryComponentDto > getSalaryComponentById(@PathVariable("id") String id) {
        SalaryComponentDto component = service.getSalaryComponentById(id);
        return ResponseEntity.ok(component);
    }

    //Update salary component
    @PutMapping("/{id}")
    public ResponseEntity<SalaryComponentResponseDto> updateSalaryComponent(
            @PathVariable("id") String id,
            @Valid @RequestBody SalaryComponentDto dto) {

        SalaryComponentResponseDto updated = service.updateSalaryComponent(id, dto);
        return ResponseEntity.ok(updated);
    }

      // Delete salary component
    @DeleteMapping("/{id}")
    public ResponseEntity<SalaryComponentResponseDto> deleteSalaryComponent(
            @PathVariable String id) {

        return ResponseEntity.ok(service.deleteSalaryComponent(id));
    }
}