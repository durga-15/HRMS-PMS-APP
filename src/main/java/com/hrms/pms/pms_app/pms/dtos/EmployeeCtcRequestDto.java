package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class EmployeeCtcRequestDto {

    @NotNull
    private UUID empId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal ctc;
}
