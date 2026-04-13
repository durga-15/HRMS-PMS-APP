package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryComponentDto {

    @Null(message = "Id must not be provided in request")
    private UUID id;

    @NotBlank(message = "Salary component name is required")
    @Size(max = 100, message = "Salary component name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Salary component type is required")
    private ComponentType type;
    private Boolean isActive;

    @Null(message = "createdAt must not be provided in request")
    private Instant createdAt;

    @Null(message = "updatedAt must not be provided in request")
    private Instant updatedAt;

    private String message;

}
