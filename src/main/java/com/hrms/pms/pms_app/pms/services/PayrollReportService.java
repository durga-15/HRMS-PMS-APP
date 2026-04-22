package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.PageResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollSummaryRequest;

public interface PayrollReportService {
    PageResponse<PayrollEmployeeSummaryProjection> getPayrollSummary(PayrollSummaryRequest request);
}
