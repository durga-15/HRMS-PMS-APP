package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "emp_pay_structure")
public class EmpPayStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emp_pay_struct_id")
    private UUID id;

    // Employee FK
    @Column(name = "emp_id", nullable = false)
    private UUID empId;

    // PayStructure FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pay_struct_id", nullable = false)
    private PayStructure payStructure;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private UUID createdBy;
    private UUID updatedBy;

    private Long createdOn;
    private Long updatedOn;

    @PrePersist
    public void onCreate() {
        long now = System.currentTimeMillis();
        createdOn = now;
        updatedOn = now;
    }

    @PreUpdate
    public void onUpdate() {
        updatedOn = System.currentTimeMillis();
    }
}