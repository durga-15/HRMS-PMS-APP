package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.PageResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollSummaryRequest;
import com.hrms.pms.pms_app.pms.services.PayrollEmployeeSummaryProjection;
import com.hrms.pms.pms_app.pms.services.PayrollReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payroll/reports")
@RequiredArgsConstructor
public class PayrollReportController {

    private final PayrollReportService service;

    @PostMapping("/summary")
    public ResponseEntity<PageResponse<PayrollEmployeeSummaryProjection>> getPayrollSummary(
            @RequestBody PayrollSummaryRequest request
    ) {
        return ResponseEntity.ok(service.getPayrollSummary(request));
    }
}
