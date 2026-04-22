package com.hrms.pms.pms_app.pms.services;

import java.util.UUID;

public interface PayrollEmployeeSummaryProjection {

    UUID getEmpSalaryId();
    UUID getPayrollDetailsId();

    UUID getEmpId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    UUID getDeptId();

    Long getMonth();
    Long getYear();

    Double getGrossSalary();
    Double getTotalDeductions();
    Double getNetSalary();

    String getStatus();
}
