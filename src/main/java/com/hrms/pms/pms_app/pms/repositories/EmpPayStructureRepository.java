package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmpPayStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmpPayStructureRepository extends JpaRepository<EmpPayStructure, UUID> {

    Optional<EmpPayStructure> findByEmpIdAndIsActiveTrue(UUID empId);

    List<EmpPayStructure> findByIsActiveTrue();
}
