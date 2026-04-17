package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employee_salary")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID empSalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private PayRollBatch batch;

    @Column(name = "emp_id", nullable = false)
    private UUID empId;

    @Column(name = "gross_salary")
    private BigDecimal grossSalary;

    @Column(name = "total_deductions")
    private BigDecimal totalDeductions;

    @Column(name = "net_salary")
    private BigDecimal netSalary;

    private String status;

    private UUID createdBy;
    private Long createdOn;
    private UUID updatedBy;
    private Long updatedOn;

    @PrePersist
    public void onCreate() {
        long now = System.currentTimeMillis();
        createdOn = now;
        updatedOn = now;
    }
}
