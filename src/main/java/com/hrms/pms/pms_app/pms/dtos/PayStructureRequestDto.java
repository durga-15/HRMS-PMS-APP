package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayStructureRequestDto {

    @Null(message = "Id must not be provided in request")
    private UUID id;

    @NotNull(message = "Salary component id is required")
    private UUID salaryComponentId;  // FK to SalaryComponent

    @NotNull(message = "Employment type id is required")
    private UUID employmentTypeId;   // FK to EmploymentType

    private CalculationType calculationType;
    private CalculationBase calculationBase;

    @DecimalMin(value = "0.01", message = "Percentage must be greater than 0")
    @DecimalMax(value = "100.00", message = "Percentage must not exceed 100")
    private BigDecimal percentage;

    @PositiveOrZero(message = "Fixed amount must be zero or positive")
    private BigDecimal fixedAmount;

    private Boolean isOptional;
    private Boolean isActive;
}
