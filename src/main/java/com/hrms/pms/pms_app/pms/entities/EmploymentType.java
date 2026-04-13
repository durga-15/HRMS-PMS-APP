package com.hrms.pms.pms_app.pms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "employee_type",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_employment_type_name", columnNames = "name")
        }
)
public class EmploymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emp_type_id")
    private UUID id;

    @Column(name = "emp_type_name", nullable = false, unique = true)
    private String name; // TRAINEE, PERMANENT, CONTRACTUAL

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @Column(name = "created_by")
    private UUID createdBy;

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
