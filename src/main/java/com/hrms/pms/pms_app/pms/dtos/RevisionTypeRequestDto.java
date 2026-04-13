package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevisionTypeRequestDto {

    @NotBlank(message = "Revision name is required")
    @jakarta.validation.constraints.Size(max = 100, message = "Revision name must not exceed 100 characters")
    private String revisionName;

    @NotNull(message = "Category is required")
    private RevisionCategory category;
}
