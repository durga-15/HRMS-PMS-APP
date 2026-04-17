package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, UUID> {
    Optional<SalaryComponent> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
    SalaryComponent findByNameIgnoreCaseAndIsActiveTrue(String name);
}
