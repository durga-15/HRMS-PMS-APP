package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RevisionDto {
    private String revisionName;
    private String category;
    private BigDecimal amount;
}
