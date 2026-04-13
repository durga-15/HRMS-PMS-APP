package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.config.SecurityUtils;
import com.hrms.pms.pms_app.pms.dtos.DesignationListResponseDto;
import com.hrms.pms.pms_app.pms.dtos.DesignationRequestDto;
import com.hrms.pms.pms_app.pms.dtos.DesignationResponseDto;
import com.hrms.pms.pms_app.pms.entities.Designation;
import com.hrms.pms.pms_app.pms.repositories.DesignationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    public DesignationResponseDto addDesignation(DesignationRequestDto request){

        UUID userId = SecurityUtils.getCurrentUserId();

        Designation designation = Designation.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .isActive(true)
                .createdBy(userId)
                .updatedBy(userId)
                .build();

        designationRepository.save(designation);

        return new DesignationResponseDto("Designation added successfully");

    }

    public List<DesignationListResponseDto> getActiveDesignations() {

        List<Designation> designations = designationRepository.findByIsActiveTrue();

        return designations.stream().map(desig -> DesignationListResponseDto.builder()
                .id(desig.getId())
                .title(desig.getTitle())
                .description(desig.getDescription())
                .isActive(desig.getIsActive())
                .createdAt(Instant.from(desig.getCreatedAt()))
                .build()
        ).collect(Collectors.toList());
    }

    public DesignationListResponseDto getDesignationByID(UUID id){

        Designation desig = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        return DesignationListResponseDto.builder()
                .id(desig.getId())
                .title(desig.getTitle())
                .description(desig.getDescription())
                .isActive(desig.getIsActive())
                .createdAt(Instant.from(desig.getCreatedAt()))
                .build();
    }


    public DesignationResponseDto updateDesignation(UUID id, DesignationRequestDto request){
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        UUID userId = SecurityUtils.getCurrentUserId();
        designation.setTitle(request.getTitle());
        designation.setDescription(request.getDescription());
        designation.setIsActive(request.getIsActive());
        designation.setUpdatedBy(userId);

        designationRepository.save(designation);

        return new DesignationResponseDto("Designation updated successfully");

    }

    public DesignationResponseDto deactivateDesignation(UUID id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        if (!designation.getIsActive()) {
            throw new RuntimeException("Designation already deactivated");
        }

        designation.setIsActive(false);
        designationRepository.save(designation);

        return new DesignationResponseDto("Designation deactivated successfully");
    }


    public DesignationResponseDto deleteDesignation(UUID id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        designationRepository.delete(designation);

        return new DesignationResponseDto("Designation deleted successfully");
    }

}

