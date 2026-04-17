package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcResponseDto;
import com.hrms.pms.pms_app.pms.services.EmployeeCtcService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employee-ctc")
@RequiredArgsConstructor
public class EmployeeCtcController {

    private final EmployeeCtcService service;

    @PostMapping
    public ResponseEntity<EmployeeCtcResponseDto> create(
            @RequestBody @Valid EmployeeCtcRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeCtcResponseDto> getActive(
            @PathVariable UUID empId) {

        return ResponseEntity.ok(service.getActiveByEmpId(empId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeCtcResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid EmployeeCtcRequestDto dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivate(@PathVariable UUID id) {

        service.deactivate(id);
        return ResponseEntity.ok("CTC deactivated successfully");
    }
}