package com.hrms.pms.pms_app.pms.services;

import java.math.BigDecimal;

public interface EmployeeRevisionProjection {

    String getRevisionName();   // maps r.revisionName
    String getCategory();       // maps r.category (EARNING / DEDUCTION)
    BigDecimal getAmount();     // maps er.amount
}
