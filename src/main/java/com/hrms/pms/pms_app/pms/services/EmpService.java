package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.EmpRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpResponseDto;
import com.hrms.pms.pms_app.pms.entities.Employee;

import java.util.List;
import java.util.UUID;

public interface EmpService {

    EmpResponseDto addEmployee(EmpRequestDto request);

    EmpResponseDto updateEmployee(UUID id, EmpRequestDto request);

    EmpResponseDto deactivateEmployee(UUID id);

    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();
}
