package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmployeeRevision;
import com.hrms.pms.pms_app.pms.services.EmployeeRevisionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface EmployeeRevisionRepository extends JpaRepository<EmployeeRevision, UUID> {

    @Query("""
    SELECT
    r.revisionName AS revisionName,
    r.category AS category,
    er.amount AS amount
    FROM EmployeeRevision er
    JOIN er.revisionType r
    JOIN er.payRollDetails pd
    JOIN er.employeeSalary es
    WHERE es.empId = :empId
    AND pd.month = :month
    AND pd.year = :year
""")
    List<EmployeeRevisionProjection> findRevisions(
            UUID empId,
            Long month,
            Long year
    );
}
