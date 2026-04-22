package com.hrms.pms.pms_app.pms.entities;

import com.hrms.pms.pms_app.pms.dtos.RevisionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "revision_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "revision_id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "revision_name", nullable = false, unique = true)
    private String revisionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RevisionCategory category;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();


    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @Column(name = "updated_by")
    private UUID updatedBy;

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