package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.PayRollDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayRollDetailsRepository extends JpaRepository<PayRollDetails, UUID> {

    boolean existsByMonthAndYearAndIsActiveTrue(Long month, Long year);

}
