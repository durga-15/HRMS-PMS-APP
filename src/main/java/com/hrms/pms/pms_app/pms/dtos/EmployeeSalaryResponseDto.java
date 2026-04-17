package com.hrms.pms.pms_app.pms.dtos;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeSalaryResponseDto {

    private UUID empId;
    private BigDecimal grossSalary;
    private BigDecimal totalDeductions;
    private BigDecimal netSalary;

    private List<ComponentDto> components;

    // getters & setters
}
