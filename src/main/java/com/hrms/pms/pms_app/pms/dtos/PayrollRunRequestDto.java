package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRunRequestDto {

    @NotNull
    private Long month;

    @NotNull
    private Long year;

    @NotNull
    private UUID payCycleId;
}
