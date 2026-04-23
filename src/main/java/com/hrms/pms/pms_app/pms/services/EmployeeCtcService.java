package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcResponseDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeCtcService {

    EmployeeCtcResponseDto create(EmployeeCtcRequestDto dto);

    EmployeeCtcResponseDto getActiveByEmpId(UUID empId);

    EmployeeCtcResponseDto update(UUID id, EmployeeCtcRequestDto dto);

    void deactivate(UUID id);

    List<EmployeeCtcResponseDto> getAll();
}
