package com.hrms.pms.pms_app.pms.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCtcResponseDto {

    private UUID empCtcId;
    private UUID empId;
    private BigDecimal ctc;
    private Boolean isActive;
}
