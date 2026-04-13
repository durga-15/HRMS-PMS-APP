package com.hrms.pms.pms_app.pms.entities;

import com.hrms.pms.pms_app.pms.dtos.CalculationBase;
import com.hrms.pms.pms_app.pms.dtos.CalculationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "pay_structure",
        indexes = {
                @Index(name = "idx_comp_id", columnList = "comp_id"),
                @Index(name = "idx_employment_type_id", columnList = "employment_type_id")
        }
)
public class PayStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pay_struct_id")
    private UUID id;

    //  Salary Component FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comp_id", nullable = false)
    private SalaryComponent salaryComponent;

    //  Employment Type FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_type_id", nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private CalculationType calculationType;

    @Enumerated(EnumType.STRING)
    private CalculationBase calculationBase;

    @Column(name = "percentage", precision = 10, scale = 2)
    private BigDecimal percentage;

    @Column(name = "fixed_amount", precision = 12, scale = 2)
    private BigDecimal fixedAmount;

    @Column(name = "is_optional", nullable = false)
    private Boolean isOptional = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        if(createdAt == null) createdAt = now;
        updatedAt = now;

    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = Instant.now();
    }
}
