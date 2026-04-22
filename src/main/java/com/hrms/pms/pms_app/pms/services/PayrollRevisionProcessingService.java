package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.PayrollRevisionProcessRequest;
import com.hrms.pms.pms_app.pms.dtos.PayrollRevisionProcessResponse;

public interface PayrollRevisionProcessingService {

    /**
     * Processes employee revisions for a given payroll cycle.
     *
     * @param request contains payrollDetailsId, month, year
     * @return processing summary response
     */
    PayrollRevisionProcessResponse process(PayrollRevisionProcessRequest request);
}
