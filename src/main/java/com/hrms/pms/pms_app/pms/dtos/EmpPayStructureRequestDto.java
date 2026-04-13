package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpPayStructureRequestDto {

    @NotNull
    private UUID empId;

    @NotNull
    private UUID payStructureId;
}
