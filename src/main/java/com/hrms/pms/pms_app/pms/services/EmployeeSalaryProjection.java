package com.hrms.pms.pms_app.pms.services;

import java.math.BigDecimal;
import java.util.UUID;

public interface EmployeeSalaryProjection {

    UUID getEmpId();
    UUID getEmpSalId();

    String getFirstName();
    String getLastName();

    BigDecimal getGrossSalary();
    BigDecimal getTotalDeductions();
    BigDecimal getNetSalary();

    UUID getCompId();
    String getCompName();
    String getCompType();
    BigDecimal getAmount();
}