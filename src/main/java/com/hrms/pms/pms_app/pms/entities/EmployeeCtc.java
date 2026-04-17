package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "employee_ctc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCtc {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emp_ctc_id", nullable = false, updatable = false)
    private UUID empCtcId;

    // ✅ Proper relation
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "ctc", nullable = false, precision = 15, scale = 2)
    private BigDecimal ctc;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdOn = now;
        updatedOn = now;
        isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = Instant.now();
    }
}