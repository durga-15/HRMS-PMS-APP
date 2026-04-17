package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunResponseDto;

public interface PayrollService {

    ApiResponse<PayrollRunResponseDto> runPayroll(PayrollRunRequestDto dto);

}
