package com.hrms.pms.pms_app.pms.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeSalaryRequestDto {
    private UUID empId;
    private Long month;
    private Long year;
}
