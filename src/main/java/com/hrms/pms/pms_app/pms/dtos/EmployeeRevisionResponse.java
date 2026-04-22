package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class EmployeeRevisionResponse {
    private UUID revisionEntryId;
    private UUID empSalId;
    private BigDecimal amount;
    private String status;
}
