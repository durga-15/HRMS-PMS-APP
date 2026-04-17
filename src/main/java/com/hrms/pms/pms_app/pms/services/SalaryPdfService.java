package com.hrms.pms.pms_app.pms.services;

import java.util.UUID;

public interface SalaryPdfService {
    byte[] generateSalaryPdf(UUID empId, Long month, Long year);
}
