package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.*;
import com.hrms.pms.pms_app.pms.entities.EmploymentType;
import com.hrms.pms.pms_app.pms.entities.PayStructure;
import com.hrms.pms.pms_app.pms.entities.SalaryComponent;
import com.hrms.pms.pms_app.pms.repositories.EmploymentTypeRepository;
import com.hrms.pms.pms_app.pms.repositories.PayStructureRepository;
import com.hrms.pms.pms_app.pms.repositories.SalaryComponentRepository;
import com.hrms.pms.pms_app.pms.services.PayStructureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PayStructureServiceImpl implements PayStructureService {

    private final PayStructureRepository payStructureRepository;
    private final SalaryComponentRepository salaryComponentRepository;
    private final EmploymentTypeRepository employmentTypeRepository;

    // ================= CREATE =================
    @Override
    public ApiResponse<PayStructureResponseDto>  create(PayStructureRequestDto dto) {

        validate(dto);

        SalaryComponent component = salaryComponentRepository.findById(dto.getSalaryComponentId())
                .orElseThrow(() -> new RuntimeException("Salary Component not found"));

        EmploymentType employmentType = employmentTypeRepository.findById(dto.getEmploymentTypeId())
                .orElseThrow(() -> new RuntimeException("Employment Type not found"));

        PayStructure entity = PayStructure.builder()
                .salaryComponent(component)
                .employmentType(employmentType)
                .calculationType(dto.getCalculationType())
                .calculationBase(dto.getCalculationBase())
                .percentage(dto.getPercentage())
                .fixedAmount(dto.getFixedAmount())
                .isOptional(Boolean.TRUE.equals(dto.getIsOptional()))
                .isActive(true)
                .build();

        PayStructure saved = payStructureRepository.save(entity);
        PayStructureResponseDto mappedDto = mapToDto(saved);

        return ApiResponse.<PayStructureResponseDto>builder()
                .message("Pay structure created successfully")
                .data(mappedDto)
                .build();
    }

    // ================= UPDATE =================
    @Override
    public PayStructureResponseDto update(UUID id, PayStructureRequestDto dto) {

        PayStructure entity = payStructureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pay Structure not found"));

        validate(dto);

        if (dto.getSalaryComponentId() != null) {
            SalaryComponent component = salaryComponentRepository.findById(dto.getSalaryComponentId())
                    .orElseThrow(() -> new RuntimeException("Salary Component not found"));
            entity.setSalaryComponent(component);
        }

        if (dto.getEmploymentTypeId() != null) {
            EmploymentType empType = employmentTypeRepository.findById(dto.getEmploymentTypeId())
                    .orElseThrow(() -> new RuntimeException("Employment Type not found"));
            entity.setEmploymentType(empType);
        }

        entity.setCalculationType(dto.getCalculationType());
        entity.setCalculationBase(dto.getCalculationBase());
        entity.setPercentage(dto.getPercentage());
        entity.setFixedAmount(dto.getFixedAmount());

        if (dto.getIsOptional() != null) {
            entity.setIsOptional(dto.getIsOptional());
        }

        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        PayStructure updated = payStructureRepository.save(entity);

        return mapToDto(updated);
    }

    // ================= GET BY ID =================
    @Override
    @Transactional
    public PayStructureResponseDto getById(UUID id) {

        PayStructure entity = payStructureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pay Structure not found"));

        return mapToDto(entity);
    }

    // ================= GET BY EMPLOYMENT TYPE =================
    @Override
    @Transactional
    public List<PayStructureResponseDto> getByEmploymentType(UUID employmentTypeId) {

        return payStructureRepository
                .findByEmploymentTypeIdAndIsActiveTrueOrderById(employmentTypeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ================= DEACTIVATE =================
    @Override
    public void deactivate(UUID id) {

        PayStructure entity = payStructureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pay Structure not found with id: " + id));

        entity.setIsActive(false);
        payStructureRepository.save(entity);
    }

    // ================= VALIDATION =================
    private void validate(PayStructureRequestDto dto) {

        if (dto.getCalculationType() == null) {
            throw new RuntimeException("Calculation type is required");
        }

        if (dto.getCalculationType() == CalculationType.PERCENTAGE) {
            if (dto.getPercentage() == null) {
                throw new RuntimeException("Percentage must be provided for PERCENTAGE type");
            }
            dto.setFixedAmount(null); // enforce rule
        }

        if (dto.getCalculationType() == CalculationType.FIXED) {
            if (dto.getFixedAmount() == null) {
                throw new RuntimeException("Fixed amount must be provided for FIXED type");
            }
            dto.setPercentage(null); // enforce rule
        }
    }

    // ================= MAPPER =================
    private PayStructureResponseDto mapToDto(PayStructure entity) {

        return PayStructureResponseDto.builder()
                .payStructId(entity.getId())
                .compId(entity.getSalaryComponent().getId())
                .compName(entity.getSalaryComponent().getName())
                .calculationType(entity.getCalculationType())
                .calculationBase(entity.getCalculationBase())
                .percentage(entity.getPercentage())
                .fixedAmount(entity.getFixedAmount())
                .isActive(entity.getIsActive())
                .build();
    }
}