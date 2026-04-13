package com.hrms.pms.pms_app.pms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollCycleResponseDto {
    private UUID id;
    private String cycleName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate payoutDate;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
