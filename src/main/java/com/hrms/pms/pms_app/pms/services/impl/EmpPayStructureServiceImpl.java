package com.hrms.pms.pms_app.pms.services.impl;


import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpPayStructureResponseDto;
import com.hrms.pms.pms_app.pms.entities.EmpPayStructure;
import com.hrms.pms.pms_app.pms.entities.PayStructure;
import com.hrms.pms.pms_app.pms.repositories.EmpPayStructureRepository;
import com.hrms.pms.pms_app.pms.repositories.PayStructureRepository;
import com.hrms.pms.pms_app.pms.services.EmpPayStructureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpPayStructureServiceImpl implements EmpPayStructureService {

    private final EmpPayStructureRepository repository;
    private final PayStructureRepository payStructureRepository;

    @Override
    public ApiResponse<EmpPayStructureResponseDto> assign(EmpPayStructureRequestDto dto) {

        // deactivate existing
        repository.findByEmpIdAndIsActiveTrue(dto.getEmpId())
                .ifPresent(existing -> {
                    existing.setIsActive(false);
                    repository.save(existing);
                });

        PayStructure payStructure = payStructureRepository.findById(dto.getPayStructureId())
                .orElseThrow(() -> new RuntimeException("Pay structure not found"));

        EmpPayStructure entity = EmpPayStructure.builder()
                .empId(dto.getEmpId())
                .payStructure(payStructure)
                .isActive(true)
                .build();

        EmpPayStructure saved = repository.save(entity);

        return ApiResponse.<EmpPayStructureResponseDto>builder()
                .message("Pay structure assigned successfully")
                .data(mapToDto(saved))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<EmpPayStructureResponseDto> getByEmployee(UUID empId) {

        EmpPayStructure entity = repository.findByEmpIdAndIsActiveTrue(empId)
                .orElseThrow(() -> new RuntimeException("No active pay structure"));

        return ApiResponse.<EmpPayStructureResponseDto>builder()
                .message("Success")
                .data(mapToDto(entity))
                .build();
    }

    @Override
    public ApiResponse<List<EmpPayStructureResponseDto>> getAllActive() {

        List<EmpPayStructureResponseDto> list = repository.findByIsActiveTrue()
                .stream()
                .map(this::mapToDto)
                .toList();

        return ApiResponse.<List<EmpPayStructureResponseDto>>builder()
                .message("Success")
                .data(list)
                .build();
    }

    @Override
    public ApiResponse<String> deactivate(UUID id) {

        EmpPayStructure entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        entity.setIsActive(false);
        repository.save(entity);

        return ApiResponse.<String>builder()
                .message("Deactivated successfully")
                .data("OK")
                .build();
    }

    private EmpPayStructureResponseDto mapToDto(EmpPayStructure e) {

        return EmpPayStructureResponseDto.builder()
                .empPayStructId(e.getId())
                .empId(e.getEmpId())
                .payStructureId(e.getPayStructure().getId())
                .build();
    }
}