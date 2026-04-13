package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.TaxSlab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaxSlabRepository extends JpaRepository<TaxSlab, UUID> {

    List<TaxSlab> findByFinancialYearAndIsActiveTrue(String financialYear);

    boolean existsByFinancialYearAndMinIncomeLessThanEqualAndMaxIncomeGreaterThanEqual(
            String financialYear,
            Double maxIncome,
            Double minIncome
    );
}
