package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.PayStructureRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayStructureResponseDto;

import java.util.List;
import java.util.UUID;

public interface PayStructureService {

    // ================= CREATE =================
    ApiResponse<PayStructureResponseDto> create(PayStructureRequestDto dto);

    // ================= UPDATE =================
    PayStructureResponseDto update(UUID id, PayStructureRequestDto dto);

    // ================= GET =================
    PayStructureResponseDto getById(UUID id);

    List<PayStructureResponseDto> getByEmploymentType(UUID employmentTypeId);

    // ================= DELETE (SOFT) =================
    void deactivate(UUID id);

    // ================= OPTIONAL (GOOD FOR PRODUCTION) =================

//    // Activate again if needed
//    void activate(UUID id);
//
//    // Get all active structures
//    List<PayStructureResponseDto> getAllActive();
//
//    // Hard delete (rarely used, admin only)
//    void delete(UUID id);
}
