package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureResponseDto;

import java.util.List;
import java.util.UUID;

public interface EmpPayStructureService {

    ApiResponse<EmpPayStructureResponseDto> assign(EmpPayStructureRequestDto dto);

    ApiResponse<EmpPayStructureResponseDto> getByEmployee(UUID empId);

    ApiResponse<List<EmpPayStructureResponseDto>> getAllActive();

    ApiResponse<String> deactivate(UUID id);
}
