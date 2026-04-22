package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PayrollRevisionProcessRequest {

    @NotNull
    private UUID payRollDetailsId;

    @NotNull
    private Long month;

    @NotNull
    private Long year;
}
