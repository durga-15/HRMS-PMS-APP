package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.config.SecurityUtils;
import com.hrms.pms.pms_app.pms.dtos.EmpRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpResponseDto;
import com.hrms.pms.pms_app.pms.entities.Department;
import com.hrms.pms.pms_app.pms.entities.Designation;
import com.hrms.pms.pms_app.pms.entities.Employee;
import com.hrms.pms.pms_app.pms.repositories.DepartmentRepository;
import com.hrms.pms.pms_app.pms.repositories.DesignationRepository;
import com.hrms.pms.pms_app.pms.repositories.EmpRepository;
import com.hrms.pms.pms_app.pms.services.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

    private final EmpRepository repository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    @Override
    public EmpResponseDto addEmployee(EmpRequestDto request) {

//        UUID userId = SecurityUtils.getCurrentUserId();

        Department department = departmentRepository.findById(request.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new IllegalArgumentException("Designation not found"));

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .department(department)
                .designation(designation)
                .panNum(request.getPanNum())
                .aadharNum(request.getAadharNum())
                .passportNum(request.getPassportNum())
                .joinDate(request.getJoinDate())
                .offerLetterNum(request.getOfferLetterNum())
                .releaseDate(request.getReleaseDate())
                .reportingManager(request.getReportingManager())
                .noticePeriod(request.getNoticePeriod())
                .isActive(true)
//                .createdBy(userId)
//                .updatedBy(userId)
                .build();

        repository.save(employee);

        return EmpResponseDto.builder()
                .message("Employee added successfully")
                .build();
    }

    @Override
    public EmpResponseDto updateEmployee(UUID id, EmpRequestDto request) {

        UUID userId = SecurityUtils.getCurrentUserId();

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if (request.getDeptId() != null) {
            Department department = departmentRepository.findById(request.getDeptId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            employee.setDepartment(department);
        }

        if (request.getDesignationId() != null) {
            Designation designation = designationRepository.findById(request.getDesignationId())
                    .orElseThrow(() -> new IllegalArgumentException("Designation not found"));
            employee.setDesignation(designation);
        }

        if (request.getFirstName() != null)
            employee.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            employee.setLastName(request.getLastName());

        if (request.getEmail() != null)
            employee.setEmail(request.getEmail());

        if (request.getPhone() != null)
            employee.setPhone(request.getPhone());

        if (request.getAddress() != null)
            employee.setAddress(request.getAddress());

        if (request.getPanNum() != null)
            employee.setPanNum(request.getPanNum());

        if (request.getAadharNum() != null)
            employee.setAadharNum(request.getAadharNum());

        if (request.getPassportNum() != null)
            employee.setPassportNum(request.getPassportNum());

        if (request.getJoinDate() != null)
            employee.setJoinDate(request.getJoinDate());

        if (request.getOfferLetterNum() != null)
            employee.setOfferLetterNum(request.getOfferLetterNum());

        if (request.getReleaseDate() != null)
            employee.setReleaseDate(request.getReleaseDate());

        if (request.getReportingManager() != null)
            employee.setReportingManager(request.getReportingManager());

        if (request.getNoticePeriod() != null)
            employee.setNoticePeriod(request.getNoticePeriod());

            employee.setUpdatedBy(userId);

        employee.setUpdatedOn(Instant.now());

        repository.save(employee);

        return EmpResponseDto.builder()
                .message("Employee updated successfully")
                .build();
    }

    @Override
    public EmpResponseDto deactivateEmployee(UUID id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        employee.setIsActive(false);
        employee.setUpdatedOn(Instant.now());

        repository.save(employee);

        return EmpResponseDto.builder()
                .message("Employee deactivated successfully")
                .build();
    }

    @Override
    public Employee getEmployeeById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findByIsActiveTrue();
    }
}
