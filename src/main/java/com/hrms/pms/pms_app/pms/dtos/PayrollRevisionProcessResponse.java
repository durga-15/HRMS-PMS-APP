package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PayrollRevisionProcessResponse {

    private UUID payRollDetailsId;
    private int totalEmployeesProcessed;
    private int totalRevisionsCreated;
    private String status;
}
