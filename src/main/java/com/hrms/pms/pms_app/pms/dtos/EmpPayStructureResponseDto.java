package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmpPayStructureResponseDto {

    private UUID empPayStructId;
    private UUID empId;
    private UUID payStructureId;
}
