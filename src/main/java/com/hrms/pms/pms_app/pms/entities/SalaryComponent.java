package com.hrms.pms.pms_app.pms.entities;

import com.hrms.pms.pms_app.pms.dtos.ComponentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "salary_components")
public class SalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comp_id", nullable = true, unique = false)
    private UUID id;

    @Column(name = "comp_name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "comp_type", nullable = false)
    private ComponentType type;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "salaryComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayStructure> payStructures;

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