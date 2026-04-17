package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "pay_roll_batch")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRollBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID batchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_roll_details_id")
    private PayRollDetails payRollDetails;

    private String batchName;
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
