package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmployeeSalary;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryProjection;
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
}