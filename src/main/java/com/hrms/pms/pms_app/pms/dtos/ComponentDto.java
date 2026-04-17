package com.hrms.pms.pms_app.pms.dtos;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ComponentDto {

    private UUID compId;
    private String compName;
    private String compType;
    private BigDecimal amount;

    // getters & setters
}