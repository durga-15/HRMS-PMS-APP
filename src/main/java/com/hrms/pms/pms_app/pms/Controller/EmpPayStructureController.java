package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureResponseDto;
import com.hrms.pms.pms_app.pms.services.EmpPayStructureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/emp-pay-structure")
@RequiredArgsConstructor
public class EmpPayStructureController {

    private final EmpPayStructureService service;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<EmpPayStructureResponseDto>> assign(
            @Valid @RequestBody EmpPayStructureRequestDto dto) {

        return ResponseEntity.ok(service.assign(dto));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ApiResponse<EmpPayStructureResponseDto>> getByEmployee(
            @PathVariable UUID empId) {

        return ResponseEntity.ok(service.getByEmployee(empId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpPayStructureResponseDto>>> getAll() {

        return ResponseEntity.ok(service.getAllActive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deactivate(@PathVariable UUID id) {

        return ResponseEntity.ok(service.deactivate(id));
    }
}
