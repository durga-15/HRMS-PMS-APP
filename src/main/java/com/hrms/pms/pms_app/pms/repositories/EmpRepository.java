package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmpRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByIsActiveTrue();

}
