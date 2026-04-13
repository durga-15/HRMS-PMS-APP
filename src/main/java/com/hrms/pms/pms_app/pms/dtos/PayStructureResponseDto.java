package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PayStructureResponseDto {

    private UUID payStructId;
    private UUID compId;
    private String compName;

    private CalculationType calculationType;
    private CalculationBase calculationBase;

    private BigDecimal percentage;
    private BigDecimal fixedAmount;
}
