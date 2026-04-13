package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PayrollCycleRequestDto {

    @NotBlank(message = "Cycle name is required")
    @Size(max = 100, message = "Cycle name must not exceed 100 characters")
    private String cycleName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Payout date is required")
    private LocalDate payoutDate;
    private UUID createdBy;
}
