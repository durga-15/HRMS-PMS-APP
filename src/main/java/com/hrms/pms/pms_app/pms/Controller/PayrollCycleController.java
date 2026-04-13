package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.PayrollCycleRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollCycleResponseDto;
import com.hrms.pms.pms_app.pms.services.PayrollCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payroll-cycles")
@RequiredArgsConstructor
public class PayrollCycleController {

    private final PayrollCycleService service;

    @PostMapping
    public ResponseEntity<PayrollCycleResponseDto> create(
            @RequestBody PayrollCycleRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollCycleResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PayrollCycleResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollCycleResponseDto> update(
            @PathVariable UUID id,
            @RequestBody PayrollCycleRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
