package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "pay_roll_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRollDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pay_roll_details_id")
    private UUID id;

    private Long month;
    private Long year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_cycle_id", nullable = false)
    private PayrollCycle payrollCycle;

    private String status;
    private Long processedAt;

    private Boolean isActive = true;

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

    @PreUpdate
    public void onUpdate() {
        updatedOn = System.currentTimeMillis();
    }
}
