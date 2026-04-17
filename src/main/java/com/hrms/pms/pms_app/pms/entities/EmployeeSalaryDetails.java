package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employee_salary_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID empSalDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_sal_id")
    private EmployeeSalary employeeSalary;

    @Column(name = "comp_id", nullable = false)
    private UUID compId;

    private BigDecimal amount;

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
