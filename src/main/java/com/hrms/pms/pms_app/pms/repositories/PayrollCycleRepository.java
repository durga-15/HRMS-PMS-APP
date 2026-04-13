package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.PayrollCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface PayrollCycleRepository extends JpaRepository<PayrollCycle, UUID> {

    Optional<PayrollCycle> findByCycleName(String cycleName);

    boolean existsByCycleName(String cycleName);
}
