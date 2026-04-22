package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.PageResponse;
import com.hrms.pms.pms_app.pms.dtos.PayrollSummaryRequest;
import com.hrms.pms.pms_app.pms.repositories.EmployeeSalaryRepository;
import com.hrms.pms.pms_app.pms.services.PayrollEmployeeSummaryProjection;
import com.hrms.pms.pms_app.pms.services.PayrollReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayrollReportServiceImpl implements PayrollReportService {

    private final EmployeeSalaryRepository repository;

    @Override
    public PageResponse<PayrollEmployeeSummaryProjection> getPayrollSummary(PayrollSummaryRequest request) {

        Sort sort = request.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(request.getSortBy()).descending()
                : Sort.by(request.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<PayrollEmployeeSummaryProjection> pageResult =
                repository.getPayrollSummary(
                        request.getMonth(),
                        request.getYear(),
                        request.getDeptId(),
                        request.getEmpId(),
                        pageable
                );

        return PageResponse.<PayrollEmployeeSummaryProjection>builder()
                .content(pageResult.getContent())
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }
}
