package com.hrms.pms.pms_app.pms.services;
import com.hrms.pms.pms_app.pms.dtos.EmploymentTypeDto;

import java.util.List;
import java.util.UUID;

public interface EmploymentTypeService {

    EmploymentTypeDto createEmploymentType(EmploymentTypeDto dto);

    List<EmploymentTypeDto> getAllEmploymentTypes();

    EmploymentTypeDto getEmploymentTypeById(UUID id);

    EmploymentTypeDto updateEmploymentType(UUID id, EmploymentTypeDto dto);

    void deleteEmploymentType(UUID id);
}
