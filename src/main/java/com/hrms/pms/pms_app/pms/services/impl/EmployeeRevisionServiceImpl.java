package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionRequest;
import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionResponse;
import com.hrms.pms.pms_app.pms.entities.*;
import com.hrms.pms.pms_app.pms.repositories.*;
import com.hrms.pms.pms_app.pms.services.EmployeeRevisionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeRevisionServiceImpl implements EmployeeRevisionService {

    private final PayRollDetailsRepository payRollDetailsRepository;
    private final PayRollBatchRepository batchRepo;
    private final EmployeeSalaryRepository salaryRepo;
    private final RevisionTypeRepository revisionTypeRepo;
    private final EmployeeRevisionRepository revisionRepo;

    @Transactional
    @Override
    public EmployeeRevisionResponse processRevision(EmployeeRevisionRequest request) {

        // 1. Validate payroll details
        PayRollDetails payroll = payRollDetailsRepository
                .findByMonthAndYear(
//                        request.getPayrollDetailsId(),
                        request.getMonth(),
                        request.getYear()
                )
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        // 2. Get batch
        PayRollBatch batch = batchRepo
                .findByPayRollDetails_Id(payroll.getId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        // 3. Get employee salary
        EmployeeSalary salary = salaryRepo
                .findByBatch_BatchIdAndEmpId(batch.getBatchId(), request.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee salary not found"));

        // 4. Validate revision type
        RevisionType revisionType = revisionTypeRepo
                .findByIdAndIsActiveTrue(request.getRevisionId())
                .orElseThrow(() -> new RuntimeException("Invalid revision type"));

        // 5. Create revision entry (FIXED)
        EmployeeRevision revision = EmployeeRevision.builder()
                .payRollDetails(payroll)
                .employeeSalary(salary)
                .revisionType(revisionType)
                .amount(request.getAmount())
                .status("PROCESSED")
                .createdBy(UUID.randomUUID()) // replace with actual user
                .build();

        revisionRepo.save(revision);

        return EmployeeRevisionResponse.builder()
                .revisionEntryId(revision.getEmpRevisionId())
                .empSalId(salary.getEmpSalId())
                .amount(revision.getAmount())
                .status(revision.getStatus())
                .build();
    }
}
