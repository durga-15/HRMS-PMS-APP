package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employee_revision")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emp_revision_id", nullable = false, updatable = false)
    private UUID empRevisionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pay_roll_details_id", nullable = false)
    private PayRollDetails payRollDetails;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_sal_id", nullable = false)
    private EmployeeSalary employeeSalary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "revision_id", nullable = false)
    private RevisionType revisionType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_on")
    private Long createdOn;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "updated_on")
    private Long updatedOn;
}