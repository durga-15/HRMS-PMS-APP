package com.hrms.pms.pms_app.pms.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class PayrollSummaryRequest {
    private Long month;
    private Long year;
    private UUID deptId;
    private UUID empId;
    private int page = 0;
    private int size = 10;
    private String sortBy = "firstName";
    private String sortDir = "asc";
}
