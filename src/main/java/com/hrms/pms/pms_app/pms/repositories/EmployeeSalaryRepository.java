package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmployeeSalary;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryProjection;
import com.hrms.pms.pms_app.pms.services.PayrollEmployeeSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, UUID> {

    @Query(value = """
    SELECT 
        es.emp_id as empId,
        es.emp_sal_id as empSalId,
        es.gross_salary as grossSalary,
        es.total_deductions as totalDeductions,
        es.net_salary as netSalary,

        sc.comp_id as compId,
        sc.comp_name as compName,
        sc.comp_type as compType,
        esd.amount as amount

    FROM employee_salary es

    JOIN pay_roll_batch pb 
        ON pb.batch_id = es.batch_id

    JOIN pay_roll_details prd 
        ON prd.pay_roll_details_id = pb.pay_roll_details_id

    LEFT JOIN employee_salary_details esd 
        ON esd.emp_sal_id = es.emp_sal_id

    LEFT JOIN salary_components sc 
        ON sc.comp_id = esd.comp_id
        AND sc.is_active = true

    WHERE es.emp_id = :empId
      AND prd.month = :month
      AND prd.year = :year
""", nativeQuery = true)
    List<EmployeeSalaryProjection> getEmployeeSalaryWithComponents(
            @Param("empId") UUID empId,
            @Param("month") Long month,
            @Param("year") Long year);

    @Query(value = """
        SELECT 
            es.emp_sal_id AS empSalaryId,
            prd.pay_roll_details_id AS payrollDetailsId,

            e.emp_id AS empId,
            e.first_name AS firstName,
            e.last_name AS lastName,
            e.email AS email,
            e.phone AS phone,
            e.dept_id AS deptId,
            e.designation_id AS desig_id,

            prd.month AS month,
            prd.year AS year,

            es.gross_salary AS grossSalary,
            es.total_deductions AS totalDeductions,
            es.net_salary AS netSalary,

            es.status AS status

        FROM employee_salary es
        JOIN employee e ON es.emp_id = e.emp_id
        JOIN pay_roll_batch prb ON es.batch_id = prb.batch_id
        JOIN pay_roll_details prd ON prb.pay_roll_details_id = prd.pay_roll_details_id

        WHERE prd.month = :month
        AND prd.year = :year
        AND prd.is_active = true
        AND e.is_active = true
        AND es.status = 'COMPLETED'

        AND (:deptId IS NULL OR e.dept_id = :deptId)
        AND (:empId IS NULL OR e.emp_id = :empId)
        """,

            countQuery = """
        SELECT COUNT(*)
        FROM employee_salary es
        JOIN employee e ON es.emp_id = e.emp_id
        JOIN pay_roll_batch prb ON es.batch_id = prb.batch_id
        JOIN pay_roll_details prd ON prb.pay_roll_details_id = prd.pay_roll_details_id

        WHERE prd.month = :month
        AND prd.year = :year
        AND prd.is_active = true
        AND e.is_active = true
        AND es.status = 'COMPLETED'

        AND (:deptId IS NULL OR e.dept_id = :deptId)
        AND (:empId IS NULL OR e.emp_id = :empId)
        """,

            nativeQuery = true)
    Page<PayrollEmployeeSummaryProjection> getPayrollSummary(
            @Param("month") Long month,
            @Param("year") Long year,
            @Param("deptId") UUID deptId,
            @Param("empId") UUID empId,
            Pageable pageable
    );

//    List<EmployeeSalary> findByBatch_BatchId(UUID batchId);
    Optional<EmployeeSalary> findByBatch_BatchIdAndEmpId(UUID batchId, UUID empId);
}