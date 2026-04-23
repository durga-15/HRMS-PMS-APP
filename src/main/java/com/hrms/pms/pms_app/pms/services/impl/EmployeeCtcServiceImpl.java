package com.hrms.pms.pms_app.pms.services.impl;
import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmployeeCtcResponseDto;
import com.hrms.pms.pms_app.pms.entities.Employee;
import com.hrms.pms.pms_app.pms.entities.EmployeeCtc;
import com.hrms.pms.pms_app.pms.repositories.EmpRepository;
import com.hrms.pms.pms_app.pms.repositories.EmployeeCtcRepository;
import com.hrms.pms.pms_app.pms.services.EmployeeCtcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeCtcServiceImpl implements EmployeeCtcService {

    private final EmployeeCtcRepository repository;
    private final EmpRepository employeeRepository;

    @Override
    public EmployeeCtcResponseDto create(EmployeeCtcRequestDto dto) {

        Employee employee = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 🔥 Only one active CTC allowed
        repository.findByEmployee_EmpIdAndIsActiveTrue(dto.getEmpId())
                .ifPresent(e -> {
                    throw new RuntimeException("Active CTC already exists");
                });

        EmployeeCtc entity = EmployeeCtc.builder()
                .employee(employee)
                .ctc(dto.getCtc())
                .isActive(true)
                .build();

        repository.save(entity);

        return mapToResponse(entity);
    }

    @Override
    public EmployeeCtcResponseDto getActiveByEmpId(UUID empId) {

        EmployeeCtc entity = repository.findByEmployee_EmpIdAndIsActiveTrue(empId)
                .orElseThrow(() -> new RuntimeException("CTC not found"));

        return mapToResponse(entity);
    }

    @Override
    public EmployeeCtcResponseDto update(UUID id, EmployeeCtcRequestDto dto) {

        EmployeeCtc entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CTC not found"));

        Employee employee = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        entity.setEmployee(employee);
        entity.setCtc(dto.getCtc());

        repository.save(entity);

        return mapToResponse(entity);
    }

    @Override
    public List<EmployeeCtcResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deactivate(UUID id) {

        EmployeeCtc entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CTC not found"));

        entity.setIsActive(false);

        repository.save(entity);
    }

    // ✅ Mapper
    private EmployeeCtcResponseDto mapToResponse(EmployeeCtc entity) {

        Employee emp = entity.getEmployee();

        String fullName = emp.getFirstName() + " " + emp.getLastName();
        return EmployeeCtcResponseDto.builder()
                .empCtcId(entity.getEmpCtcId())
                .employeeName(fullName)
                .empId(entity.getEmployee().getEmpId())
                .ctc(entity.getCtc())
                .isActive(entity.getIsActive())
                .build();
    }
}