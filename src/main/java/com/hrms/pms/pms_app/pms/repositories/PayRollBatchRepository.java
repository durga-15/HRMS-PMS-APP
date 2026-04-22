package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.PayRollBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PayRollBatchRepository extends JpaRepository<PayRollBatch, UUID> {
    List<PayRollBatch> findByPayRollDetailsId(UUID payRollDetailsId);

//    @Query("""
//    SELECT b.batchId
//    FROM PayRollBatch b
//    WHERE b.payRollDetails.id = :payRollDetailsId
//""")
//    Optional<UUID> findBatchIdByPayRollDetailsId(@Param("payRollDetailsId") UUID payRollDetailsId);

    Optional<PayRollBatch> findByPayRollDetails_Id(UUID id);

}
