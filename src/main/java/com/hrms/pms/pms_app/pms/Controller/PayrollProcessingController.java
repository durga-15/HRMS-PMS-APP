package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.services.PayrollProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollProcessingController {

    private final PayrollProcessingService service;

    /**
     * 🔥 Process Payroll (Core API)
     */
    @PostMapping("/process/{payRollDetailsId}")
    public ResponseEntity<ApiResponse<String>> processPayroll(
            @PathVariable @NotNull UUID payRollDetailsId) {

        ApiResponse<String> response = service.processPayroll(payRollDetailsId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 📊 Get Payroll Status
     */
    @GetMapping("/status/{payRollDetailsId}")
    public ResponseEntity<ApiResponse<String>> getPayrollStatus(
            @PathVariable UUID payRollDetailsId) {

        ApiResponse<String> response = service.getPayrollStatus(payRollDetailsId);

        return ResponseEntity.ok(response);
    }

    /**
     * 🔁 Retry Failed Payroll
     */
    @PostMapping("/retry/{payRollDetailsId}")
    public ResponseEntity<ApiResponse<String>> retryPayroll(
            @PathVariable UUID payRollDetailsId) {

        ApiResponse<String> response = service.retryPayroll(payRollDetailsId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 📦 Get Batch Status (Optional but very useful)
     */
    @GetMapping("/batch/{payRollDetailsId}")
    public ResponseEntity<ApiResponse<Object>> getBatchDetails(
            @PathVariable UUID payRollDetailsId) {

        ApiResponse<Object> response = service.getBatchDetails(payRollDetailsId);

        return ResponseEntity.ok(response);
    }
}
