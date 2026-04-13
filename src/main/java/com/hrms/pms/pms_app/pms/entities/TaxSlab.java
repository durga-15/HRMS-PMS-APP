package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tax_slabs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxSlab {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Double minIncome;

    @Column(nullable = false)
    private Double maxIncome;

    @Column(nullable = false)
    private Double taxPercentage;

    @Column(nullable = false)
    private String financialYear;

    @Column(nullable = false)
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

