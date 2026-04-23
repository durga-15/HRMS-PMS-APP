package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmployeeCtc;
import com.hrms.pms.pms_app.pms.entities.PayStructure;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeCtcRepository extends JpaRepository<EmployeeCtc, UUID> {
    Optional<EmployeeCtc> findByEmployee_EmpIdAndIsActiveTrue(UUID empId);

    List<EmployeeCtc> findByEmployee_EmpId(UUID empId);

    @Override
    @EntityGraph(attributePaths = "employee")
    List<EmployeeCtc> findAll();

}
