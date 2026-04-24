package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.ComponentDto;
import com.hrms.pms.pms_app.pms.dtos.ComponentType;
import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryResponseDto;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentDto;
import com.hrms.pms.pms_app.pms.entities.EmployeeSalary;
import com.hrms.pms.pms_app.pms.exception.ResourceNotFoundException;
import com.hrms.pms.pms_app.pms.repositories.EmployeeSalaryRepository;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryProjection;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService {

    private final EmployeeSalaryRepository repository;

    @Override
    public EmployeeSalaryResponseDto getSalary(UUID empId, Long month, Long year) {

        List<EmployeeSalaryProjection> data =
                repository.getEmployeeSalaryWithComponents(empId, month, year);

        if (data.isEmpty()) {
            throw new IllegalArgumentException("No salary found for given date!");
        }

        EmployeeSalaryProjection first = data.get(0);

        EmployeeSalaryResponseDto response = new EmployeeSalaryResponseDto();
        response.setEmpId(first.getEmpId());
        response.setGrossSalary(first.getGrossSalary());
        response.setTotalDeductions(first.getTotalDeductions());
        response.setNetSalary(first.getNetSalary());

        List<ComponentDto> components = data.stream()
                .map(row -> {
                    ComponentDto dto = new ComponentDto();
                    dto.setCompId(row.getCompId());
                    dto.setCompName(row.getCompName());
                    dto.setCompType(row.getCompType());
                    dto.setAmount(row.getAmount());
                    return dto;
                })
                .toList();

        response.setComponents(components);

        return response;
    }

    @Override
    public EmployeeSalaryResponseDto getSalaryOfEmployee(UUID empId, Long month, Long year) {
        List<EmployeeSalaryProjection> data = repository.getEmployeeSalaryWithComponents(empId, month, year);
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No salary found for the Given date");
        } EmployeeSalaryProjection first = data.get(0);
        EmployeeSalaryResponseDto response = new EmployeeSalaryResponseDto();
        response.setEmpId(first.getEmpId()); response.setGrossSalary(first.getGrossSalary());
        response.setTotalDeductions(first.getTotalDeductions());
        response.setNetSalary(first.getNetSalary());
        List<ComponentDto> components = data.stream() .map(row -> {
            ComponentDto dto = new ComponentDto(); dto.setCompId(row.getCompId());
            dto.setCompName(row.getCompName()); dto.setCompType(row.getCompType());
            dto.setAmount(row.getAmount()); return dto; }) .toList();
        response.setComponents(components); return response; }
}