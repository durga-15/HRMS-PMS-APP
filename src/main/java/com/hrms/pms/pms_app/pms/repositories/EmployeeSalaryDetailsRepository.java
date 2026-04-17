package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.EmployeeSalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeSalaryDetailsRepository extends JpaRepository<EmployeeSalaryDetails, UUID> {
}