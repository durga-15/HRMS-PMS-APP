package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.ApiResponse;
import com.hrms.pms.pms_app.pms.dtos.ComponentType;
import com.hrms.pms.pms_app.pms.dtos.RevisionCategory;
import com.hrms.pms.pms_app.pms.entities.*;
import com.hrms.pms.pms_app.pms.repositories.*;
import com.hrms.pms.pms_app.pms.services.PayrollProcessingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollProcessingServiceImpl implements PayrollProcessingService {

    private final PayRollDetailsRepository payRollDetailsRepository;
    private final PayRollBatchRepository batchRepository;
    private final EmpRepository employeeRepository;
    private final EmployeeCtcRepository employeeCtcRepository;
    private final PayStructureRepository payStructureRepository;
    private final EmpPayStructureRepository empPayStructureRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeSalaryDetailsRepository employeeSalaryDetailsRepository;
    private final TaxSlabRepository taxSlabRepository;
    private final SalaryComponentRepository salaryComponentRepository;

    private static final int BATCH_SIZE = 100;
    private static final int SCALE = 2;

    @Override
    public ApiResponse<String> processPayroll(UUID payRollDetailsId) {

        PayRollDetails payroll = payRollDetailsRepository.findById(payRollDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Payroll not found"));

        if ("COMPLETED".equals(payroll.getStatus())) {
            throw new IllegalArgumentException("Payroll already processed");
        }

        payroll.setStatus("PROCESSING");
        payRollDetailsRepository.save(payroll);

        List<Employee> employees = employeeRepository.findAll();
        List<List<Employee>> batches = createBatches(employees);

        int batchCount = 1;

        for (List<Employee> batchEmployees : batches) {

            PayRollBatch batch = PayRollBatch.builder()
                    .payRollDetails(payroll)
                    .batchName("Batch-" + batchCount)
                    .status("INITIATED")
                    .build();

            batch = batchRepository.save(batch);

            processBatch(batchEmployees, batch, payroll);

            batch.setStatus("COMPLETED");
            batchRepository.save(batch);

            batchCount++;
        }

        payroll.setStatus("COMPLETED");
        payroll.setProcessedAt(System.currentTimeMillis());
        payRollDetailsRepository.save(payroll);

        return ApiResponse.<String>builder()
                .message("Payroll processed successfully")
                .data("SUCCESS")
                .build();
    }

    // 🔥 Batch Split Logic
    private List<List<Employee>> createBatches(List<Employee> employees) {
        List<List<Employee>> batches = new ArrayList<>();

        for (int i = 0; i < employees.size(); i += BATCH_SIZE) {
            batches.add(employees.subList(i, Math.min(i + BATCH_SIZE, employees.size())));
        }

        return batches;
    }

    // 🔥 Core Batch Processing
    private void processBatch(List<Employee> employees,
                              PayRollBatch batch,
                              PayRollDetails payroll) {

        for (Employee emp : employees) {

            // 1. Get CTC
            EmployeeCtc ctcEntity = employeeCtcRepository
                    .findByEmployee_EmpIdAndIsActiveTrue(emp.getEmpId())
                    .orElseThrow(() -> new IllegalArgumentException("CTC not found"));

            BigDecimal annualCtc = ctcEntity.getCtc();
            BigDecimal monthlyCtc = divide(annualCtc, 12);

            // 2. Get Pay Structure Mapping
            EmpPayStructure empPay = empPayStructureRepository
                    .findByEmpIdAndIsActiveTrue(emp.getEmpId())
                    .orElseThrow(() -> new IllegalArgumentException("Pay structure not assigned"));

            List<PayStructure> components = payStructureRepository
                    .findByEmploymentTypeIdAndIsActiveTrueOrderById(
                            empPay.getPayStructure().getEmploymentType().getId()
                    );

            // 🔥 IMPORTANT: Ensure BASIC comes first
            components.sort((a, b) -> {
                if ("Basic".equalsIgnoreCase(a.getSalaryComponent().getName())) return -1;
                if ("Basic".equalsIgnoreCase(b.getSalaryComponent().getName())) return 1;
                return 0;
            });

            BigDecimal gross = BigDecimal.ZERO;
            BigDecimal deductions = BigDecimal.ZERO;

            List<EmployeeSalaryDetails> detailsList = new ArrayList<>();

            // 🔥 Dynamic calculation storage
            Map<String, BigDecimal> computed = new HashMap<>();
            computed.put("CTC", monthlyCtc);

            // 3. Calculate Components
            for (PayStructure comp : components) {

                BigDecimal amount = BigDecimal.ZERO;

                // 🔥 Get base dynamically
                BigDecimal baseValue = computed.get(comp.getCalculationBase().name());

                if ("PERCENTAGE".equals(comp.getCalculationType().name())) {

                    if (baseValue == null) {
                        throw new IllegalArgumentException("Missing base value for " + comp.getCalculationBase());
                    }

                    amount = percentage(baseValue, comp.getPercentage());

                } else if ("FIXED".equals(comp.getCalculationType().name())) {
                    amount = scale(comp.getFixedAmount());
                }

                // 🔥 Store computed value
                String compName = comp.getSalaryComponent().getName().toUpperCase();
                computed.put(compName, amount);

                // 🔥 Earnings vs Deductions
                if ("EARNING".equalsIgnoreCase(comp.getSalaryComponent().getType().name())) {
                    gross = scale(gross.add(amount));
                } else {
                    deductions = scale(deductions.add(amount));
                }

                // 🔥 Save detail row
                detailsList.add(EmployeeSalaryDetails.builder()
                        .compId(comp.getSalaryComponent().getId())
                        .amount(amount)
                        .status("ACTIVE")
                        .build());
            }

            // ✅ 3.5 Apply Employee Revisions (BONUS / REIMBURSEMENT etc.)
//            List<EmployeeRevision> revisions =
//                    employeeRevisionRepository
//                                .findByEmployee_EmpIdAndPayRollDetails_IdAndStatus(
//                                    emp.getEmpId(),
//                                    payroll.getId(),
//                                    "APPROVED"
//                            );
//
//            for (EmployeeRevision r : revisions) {
//
//                BigDecimal amount = scale(r.getAmount());
//
//                // ✔ Add to gross or deduction
//                if (r.getRevisionType().getCategory() == RevisionCategory.EARNING) {
//                    gross = scale(gross.add(amount));
//                } else {
//                    deductions = scale(deductions.add(amount));
//                }
//
//                // ✔ Add into salary details (IMPORTANT)
//                detailsList.add(EmployeeSalaryDetails.builder()
//                        .revisionType(r.getRevisionType())
//                        .compId(null)
//                        .status("ACTIVE")
//                        .build());
//            }

            // 4. Net Salary
//            BigDecimal net = scale(gross.subtract(deductions));

            // 4. Calculate Tax (AFTER gross is ready)
            BigDecimal tax = calculateTax(gross);

// Add tax to deductions
            deductions = scale(deductions.add(tax));

// ✅ Fetch TAX component
            SalaryComponent taxComponent = salaryComponentRepository
                    .findByNameIgnoreCaseAndIsActiveTrue(ComponentType.TAX.name());

            if (taxComponent == null) {
                throw new IllegalArgumentException("TAX component not configured");
            }

// ✅ Add TAX as salary detail
            detailsList.add(EmployeeSalaryDetails.builder()
                    .compId(taxComponent.getId())
                    .amount(tax)
                    .status("ACTIVE")
                    .build());

// 5. Net Salary
            BigDecimal net = scale(gross.subtract(deductions));

            // 🔍 DEBUG (remove later)
            System.out.println("Emp: " + emp.getEmpId());
            System.out.println("Gross: " + gross);
            System.out.println("Deduction: " + deductions);
            System.out.println("Net: " + net);

            // 5. Save Salary
            EmployeeSalary salary = EmployeeSalary.builder()
                    .batch(batch)
                    .empId(emp.getEmpId())
                    .grossSalary(gross)
                    .totalDeductions(deductions)
                    .netSalary(net)
                    .status("COMPLETED")
                    .build();

            salary = employeeSalaryRepository.saveAndFlush(salary);

            // 6. Save Salary Details
            for (EmployeeSalaryDetails d : detailsList) {
                d.setEmployeeSalary(salary);
            }

            employeeSalaryDetailsRepository.saveAll(detailsList);
        }
    }

    // 🔥 Utility Methods (VERY IMPORTANT)

    private BigDecimal divide(BigDecimal value, int divisor) {
        return value.divide(BigDecimal.valueOf(divisor), SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal percentage(BigDecimal base, BigDecimal percent) {
        return base.multiply(percent)
                .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal scale(BigDecimal value) {
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }

    // 🔁 Status APIs

    @Override
    public ApiResponse<String> getPayrollStatus(UUID payRollDetailsId) {

        PayRollDetails payroll = payRollDetailsRepository.findById(payRollDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Payroll not found"));

        return ApiResponse.<String>builder()
                .message("Success")
                .data(payroll.getStatus())
                .build();
    }

    @Override
    public ApiResponse<String> retryPayroll(UUID payRollDetailsId) {

        PayRollDetails payroll = payRollDetailsRepository.findById(payRollDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Payroll not found"));

        if (!"FAILED".equals(payroll.getStatus())) {
            throw new IllegalArgumentException("Only failed payroll can be retried");
        }

        payroll.setStatus("INITIATED");
        payRollDetailsRepository.save(payroll);

        processPayroll(payRollDetailsId);

        return ApiResponse.<String>builder()
                .message("Payroll retry initiated")
                .data("RETRY_STARTED")
                .build();
    }

    @Override
    public ApiResponse<Object> getBatchDetails(UUID payRollDetailsId) {

        List<PayRollBatch> batches = batchRepository
                .findByPayRollDetailsId(payRollDetailsId);

        return ApiResponse.builder()
                .message("Success")
                .data(batches)
                .build();
    }

    private BigDecimal calculateTax(BigDecimal monthlyGross) {

        String financialYear = getCurrentFinancialYear();

        List<TaxSlab> slabs = taxSlabRepository
                .findByFinancialYearAndIsActiveTrueOrderByMinIncomeAsc(financialYear);

        if (slabs.isEmpty()) {
            throw new IllegalArgumentException("No tax slabs configured for FY: " + financialYear);
        }

        BigDecimal annualIncome = monthlyGross.multiply(BigDecimal.valueOf(12));
        BigDecimal totalTax = BigDecimal.ZERO;

        for (TaxSlab slab : slabs) {

            BigDecimal min = BigDecimal.valueOf(slab.getMinIncome());
            BigDecimal max = slab.getMaxIncome() == null
                    ? annualIncome
                    : BigDecimal.valueOf(slab.getMaxIncome());

            if (annualIncome.compareTo(min) <= 0) continue;

            BigDecimal taxable = annualIncome.min(max).subtract(min);

            if (taxable.compareTo(BigDecimal.ZERO) > 0) {

                BigDecimal slabTax = taxable
                        .multiply(BigDecimal.valueOf(slab.getTaxPercentage()))
                        .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);

                totalTax = totalTax.add(slabTax);
            }

            if (annualIncome.compareTo(max) <= 0) break;
        }

        // Convert to monthly
        return totalTax.divide(BigDecimal.valueOf(12), SCALE, RoundingMode.HALF_UP);
    }

    private String getCurrentFinancialYear() {

        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        if (month >= 4) {
            // April to December → current year start
            return year + "-" + String.valueOf(year + 1).substring(2);
        } else {
            // Jan to March → previous year start
            return (year - 1) + "-" + String.valueOf(year).substring(2);
        }
    }
}