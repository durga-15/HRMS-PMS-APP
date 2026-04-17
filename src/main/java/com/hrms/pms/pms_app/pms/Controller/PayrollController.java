package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunResponseDto;
import com.hrms.pms.pms_app.pms.services.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/run")
    public ResponseEntity<ApiResponse<PayrollRunResponseDto>> runPayroll(
            @Valid @RequestBody PayrollRunRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(payrollService.runPayroll(dto));
    }
}
