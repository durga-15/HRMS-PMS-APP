package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.PayRollBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PayRollBatchRepository extends JpaRepository<PayRollBatch, UUID> {
    List<PayRollBatch> findByPayRollDetailsId(UUID payRollDetailsId);
}
