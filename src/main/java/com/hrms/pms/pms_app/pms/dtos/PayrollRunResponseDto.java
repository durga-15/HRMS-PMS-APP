package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PayrollRunResponseDto {

    private UUID payRollDetailsId;
    private Long month;
    private Long year;
    private String status;
}
