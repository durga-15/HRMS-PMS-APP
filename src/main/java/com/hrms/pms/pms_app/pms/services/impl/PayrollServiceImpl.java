package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollRunResponseDto;
import com.hrms.pms.pms_app.pms.entities.PayRollDetails;
import com.hrms.pms.pms_app.pms.entities.PayrollCycle;
import com.hrms.pms.pms_app.pms.repositories.PayRollDetailsRepository;
import com.hrms.pms.pms_app.pms.repositories.PayrollCycleRepository;
import com.hrms.pms.pms_app.pms.services.PayrollService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayRollDetailsRepository payRollDetailsRepository;
    private final PayrollCycleRepository payrollCycleRepository;

    @Override
    public ApiResponse<PayrollRunResponseDto> runPayroll(PayrollRunRequestDto dto) {

        // 1. Prevent duplicate payroll
        boolean exists = payRollDetailsRepository
                .existsByMonthAndYearAndIsActiveTrue(dto.getMonth(), dto.getYear());

        if (exists) {
            throw new RuntimeException("Payroll already exists for this month/year");
        }

        // 2. Fetch payroll cycle
        PayrollCycle cycle = payrollCycleRepository.findById(dto.getPayCycleId())
                .orElseThrow(() -> new RuntimeException("Payroll cycle not found"));

        // 3. Create payroll run
        PayRollDetails entity = PayRollDetails.builder()
                .month(dto.getMonth())
                .year(dto.getYear())
                .payrollCycle(cycle)
                .status("INITIATED")
                .isActive(true)
                .build();

        PayRollDetails saved = payRollDetailsRepository.save(entity);

        // 4. Map response
        PayrollRunResponseDto response = PayrollRunResponseDto.builder()
                .payRollDetailsId(saved.getId())
                .month(saved.getMonth())
                .year(saved.getYear())
                .status(saved.getStatus())
                .build();

        return ApiResponse.<PayrollRunResponseDto>builder()
                .message("Payroll run created successfully")
                .data(response)
                .build();
    }
}
