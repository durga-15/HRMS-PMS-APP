package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.EmpRequestDto;
import com.hrms.pms.pms_app.pms.dtos.EmpResponseDto;
import com.hrms.pms.pms_app.pms.entities.Employee;
import com.hrms.pms.pms_app.pms.services.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmpController {

    private final EmpService service;

    @PostMapping("/employees")
    public EmpResponseDto addEmployee(@RequestBody EmpRequestDto request) {
        return service.addEmployee(request);
    }

    @PatchMapping("/employees/{id}")
    public EmpResponseDto updateEmployee(@PathVariable UUID id,
                                         @RequestBody EmpRequestDto request) {
        return service.updateEmployee(id, request);
    }

    @DeleteMapping("/employees/{id}")
    public EmpResponseDto deactivateEmployee(@PathVariable UUID id) {
        return service.deactivateEmployee(id);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable UUID id) {
        return service.getEmployeeById(id);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }
}

