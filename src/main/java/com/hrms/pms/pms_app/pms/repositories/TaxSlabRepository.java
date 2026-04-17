package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.TaxSlab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxSlabRepository extends JpaRepository<TaxSlab, UUID> {

    List<TaxSlab> findByFinancialYearAndIsActiveTrue(String financialYear);

    boolean existsByFinancialYearAndMinIncomeLessThanEqualAndMaxIncomeGreaterThanEqual(
            String financialYear,
            Double maxIncome,
            Double minIncome
    );

    List<TaxSlab> findByFinancialYearAndIsActiveTrueOrderByMinIncomeAsc(String financialYear);
    @Query("""
        SELECT t FROM TaxSlab t
        WHERE t.isActive = true
        AND :income BETWEEN t.minIncome AND t.maxIncome
    """)
    Optional<TaxSlab> findApplicableSlab(@Param("income") Double income);
}
