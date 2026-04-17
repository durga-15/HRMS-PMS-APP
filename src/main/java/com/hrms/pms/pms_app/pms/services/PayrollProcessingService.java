package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;

import java.util.UUID;

public interface PayrollProcessingService {

    ApiResponse<String> processPayroll(UUID payRollDetailsId);

    ApiResponse<String> getPayrollStatus(UUID payRollDetailsId);

    ApiResponse<String> retryPayroll(UUID payRollDetailsId);

    ApiResponse<Object> getBatchDetails(UUID payRollDetailsId);

}
