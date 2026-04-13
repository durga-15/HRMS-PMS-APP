package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.dtos.PayStructureResponseDto;
import com.hrms.pms.pms_app.pms.entities.PayStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface PayStructureRepository extends JpaRepository<PayStructure, UUID> {

    boolean existsBySalaryComponentIdAndEmploymentTypeId(UUID salaryComponentId, UUID employmentTypeId);

    boolean existsBySalaryComponentIdAndEmploymentTypeIdAndIdNot(UUID salaryComponentId, UUID employmentTypeId, UUID id);

    boolean existsBySalaryComponentId(UUID salaryComponentId);

    boolean existsByEmploymentTypeId(UUID employmentTypeId);

    List<PayStructure> findByEmploymentTypeIdAndIsActiveTrueOrderById(UUID employmentTypeId);
}
