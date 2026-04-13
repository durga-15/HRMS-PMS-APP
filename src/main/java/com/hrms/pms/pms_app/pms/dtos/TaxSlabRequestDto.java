package com.hrms.pms.pms_app.pms.dtos;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaxSlabRequestDto {

    @NotNull(message = "Minimum income is required")
    @DecimalMin(value = "0.0", message = "Minimum income must be zero or positive")
    private Double minIncome;

    @NotNull(message = "Maximum income is required")
    @DecimalMin(value = "0.0", message = "Maximum income must be zero or positive")
    private Double maxIncome;

    @NotNull(message = "Tax percentage is required")
    @DecimalMin(value = "0.0", message = "Tax percentage must be zero or positive")
    @DecimalMax(value = "100.0", message = "Tax percentage must not exceed 100")
    private Double taxPercentage;

    @NotBlank(message = "Financial year is required")
    private String financialYear;
}
