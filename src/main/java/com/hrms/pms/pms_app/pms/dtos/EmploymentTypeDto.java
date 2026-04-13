package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentTypeDto {

    @Null(message = "Id must not be provided in request")
    private UUID id;

    @NotBlank(message = "Employment type name is required")
    @Size(max = 100, message = "Employment type name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    private Boolean isActive;
}
