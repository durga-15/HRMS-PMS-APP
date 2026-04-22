package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class EmployeeRevisionRequest {

    private UUID payrollDetailsId;

    @NotNull
    private Long month;

    @NotNull
    private Long year;

    @NotNull
    private UUID empId;

    @NotNull
    private UUID revisionId;

    @NotNull
    private BigDecimal amount;
}