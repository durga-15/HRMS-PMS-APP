package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.PayStructureRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayStructureResponseDto;
import com.hrms.pms.pms_app.pms.services.PayStructureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay-structures")
@RequiredArgsConstructor
public class PayStructureController {

    private final PayStructureService service;

    // ================= CREATE =================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PayStructureResponseDto>> create(
            @Valid @RequestBody PayStructureRequestDto dto) {

//        PayStructureResponseDto response = service.create(dto);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    // ================= UPDATE =================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PayStructureResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody PayStructureRequestDto dto) {

        PayStructureResponseDto response = service.update(id, dto);

        return ResponseEntity.ok(response);
    }

    // ================= GET BY ID =================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PayStructureResponseDto> getById(
            @PathVariable UUID id) {

        PayStructureResponseDto response = service.getById(id);

        return ResponseEntity.ok(response);
    }

    // ================= GET BY EMPLOYMENT TYPE =================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employment/{employmentTypeId}")
    public ResponseEntity<List<PayStructureResponseDto>> getByEmploymentType(
            @PathVariable UUID employmentTypeId) {

        List<PayStructureResponseDto> response =
                service.getByEmploymentType(employmentTypeId);

        return ResponseEntity.ok(response);
    }

    // ================= DEACTIVATE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable UUID id) {

        service.deactivate(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Pay structure deactivated successfully for ID: " + id)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}