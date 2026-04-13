package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.config.SecurityUtils;
import com.hrms.pms.pms_app.pms.dtos.DepartmentListResponseDto;
import com.hrms.pms.pms_app.pms.dtos.DepartmentRequestDto;
import com.hrms.pms.pms_app.pms.dtos.DepartmentResponseDto;
import com.hrms.pms.pms_app.pms.entities.Department;
import com.hrms.pms.pms_app.pms.repositories.DepartmentRepository;
import com.hrms.pms.pms_app.pms.security.JwtService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDto addDepartment(DepartmentRequestDto request){


        UUID userId = SecurityUtils.getCurrentUserId();

        Department department = Department.builder()
                .deptName(request.getDeptName())
                .description(request.getDescription())
                .isActive(true)
                .createdBy(userId)
                .updatedBy(userId)
                .build();

        departmentRepository.save(department);

        return new DepartmentResponseDto("Department added successfully");

    }

    public List<DepartmentListResponseDto> getActiveDepartments() {

        List<Department> departments = departmentRepository.findByIsActiveTrue();

        return departments.stream().map(dept -> DepartmentListResponseDto.builder()
                .id(dept.getId())
                .deptName(dept.getDeptName())
                .description(dept.getDescription())
                .isActive(dept.getIsActive())
                .createdAt(dept.getCreatedOn())
                .build()
        ).collect(Collectors.toList());
    }

    public DepartmentListResponseDto getDepartmentByID(UUID id){

        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return DepartmentListResponseDto.builder()
                .id(dept.getId())
                .deptName(dept.getDeptName())
                .description(dept.getDescription())
                .isActive(dept.getIsActive())
                .createdAt(dept.getCreatedOn())
                .build();
    }


    public DepartmentResponseDto updateDepartment(UUID id, DepartmentRequestDto request){

        UUID userId = SecurityUtils.getCurrentUserId();

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setDeptName(request.getDeptName());
        department.setDescription(request.getDescription());
        department.setIsActive(request.getIsActive());
        department.setUpdatedBy(userId);

        departmentRepository.save(department);

        return new DepartmentResponseDto("Department updated successfully");

    }

    public DepartmentResponseDto deactivateDepartment(UUID id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (!department.getIsActive()) {
            throw new RuntimeException("Department already deactivated");
        }

        department.setIsActive(false);
        departmentRepository.save(department);

        return new DepartmentResponseDto("Department deactivated successfully");
    }


    public DepartmentResponseDto deleteDepartment(UUID id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        departmentRepository.delete(department);

        return new DepartmentResponseDto("Department deleted successfully");
    }
}

