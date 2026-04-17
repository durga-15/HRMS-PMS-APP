package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryResponseDto;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class EmployeeSalaryController {

    private final EmployeeSalaryService service;

    @GetMapping
    public ResponseEntity<EmployeeSalaryResponseDto> getSalary(
            @RequestParam UUID empId,
            @RequestParam Long month,
            @RequestParam Long year) {

        EmployeeSalaryResponseDto response =
                service.getSalary(empId, month, year);

        return ResponseEntity.ok(response);
    }
}
