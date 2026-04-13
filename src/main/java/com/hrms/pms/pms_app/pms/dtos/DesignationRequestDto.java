package com.hrms.pms.pms_app.pms.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class DesignationRequestDto {

    private String title;
    private String description;
    private Boolean isActive;
    private UUID createdBy;
}
