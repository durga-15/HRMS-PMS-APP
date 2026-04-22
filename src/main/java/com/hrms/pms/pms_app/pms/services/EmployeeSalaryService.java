package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryResponseDto;

import java.util.UUID;

public interface EmployeeSalaryService {
    EmployeeSalaryResponseDto getSalary(UUID empId, Long month, Long year);

    EmployeeSalaryResponseDto getSalaryOfEmployee(UUID empId, Long month, Long year);
}
