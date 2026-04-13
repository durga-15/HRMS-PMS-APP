package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.DeptDesigMappingRequestDto;
import com.hrms.pms.pms_app.pms.dtos.DeptDesigMappingResponseDto;

import java.util.List;
import java.util.UUID;

public interface DeptDesigMappingService {


    DeptDesigMappingResponseDto addMappings(DeptDesigMappingRequestDto dto);

    //DeptDesigMappingResponseDto patchMapping(UUID id, DeptDesigMappingRequestDto dto);
    List<UUID> getDesignationsByDept(UUID deptId);

    DeptDesigMappingResponseDto deleteMapping(UUID deptId, UUID desigId);
}
