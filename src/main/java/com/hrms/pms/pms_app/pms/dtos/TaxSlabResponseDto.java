package com.hrms.pms.pms_app.pms.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxSlabResponseDto {

    private UUID id;
    private Double minIncome;
    private Double maxIncome;
    private Double taxPercentage;
    private String financialYear;
    private Boolean isActive;
}
