package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionRequest;
import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionResponse;

public interface EmployeeRevisionService {

    EmployeeRevisionResponse processRevision(EmployeeRevisionRequest request);

}
