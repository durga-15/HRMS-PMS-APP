package com.hrms.pms.pms_app.pms.repositories;


import com.hrms.pms.pms_app.pms.entities.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DesignationRepository extends JpaRepository<Designation, UUID> {

    List<Designation>findByIsActiveTrue();
}
