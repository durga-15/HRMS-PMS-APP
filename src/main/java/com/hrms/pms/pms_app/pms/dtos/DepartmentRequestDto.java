package com.hrms.pms.pms_app.pms.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class DepartmentRequestDto {

    private String deptName;
    private String description;
    private Boolean isActive;
    private UUID createdBy;
}
