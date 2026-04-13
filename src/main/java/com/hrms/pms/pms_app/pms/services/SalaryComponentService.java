package com.hrms.pms.pms_app.pms.services;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentDto;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentResponseDto;

import java.util.List;

public interface SalaryComponentService {
    SalaryComponentResponseDto createSalaryComponent(SalaryComponentDto salaryComponentDto);

    List<SalaryComponentDto> getAllSalaryComponents();

    SalaryComponentDto getSalaryComponentById(String id);

    SalaryComponentResponseDto updateSalaryComponent(String id, SalaryComponentDto salaryComponentDto);

    SalaryComponentResponseDto deleteSalaryComponent(String id);

}
