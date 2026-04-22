package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryResponseDto;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class EmployeeSalaryController {

    private final EmployeeSalaryService service;

    @PostMapping("/get")
    public ResponseEntity<EmployeeSalaryResponseDto> getSalary(
            @RequestBody EmployeeSalaryRequestDto request) {

        EmployeeSalaryResponseDto response =
                service.getSalary(
                        request.getEmpId(),
                        request.getMonth(),
                        request.getYear()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<EmployeeSalaryResponseDto> getSalaryOfEmployee(
            @RequestParam UUID empId,
            @RequestParam Long month,
            @RequestParam Long year) {
        EmployeeSalaryResponseDto response = service.getSalary(
                empId,
                month,
                year);
        return ResponseEntity.ok(response); }
}
