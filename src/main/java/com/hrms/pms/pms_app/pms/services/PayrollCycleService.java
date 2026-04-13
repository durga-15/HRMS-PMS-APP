package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.PayrollCycleRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollCycleResponseDto;

import java.util.List;
import java.util.UUID;

public interface PayrollCycleService {

    PayrollCycleResponseDto create(PayrollCycleRequestDto dto);

    PayrollCycleResponseDto getById(UUID id);

    List<PayrollCycleResponseDto> getAll();

    PayrollCycleResponseDto update(UUID id, PayrollCycleRequestDto dto);

    void delete(UUID id);
}
