package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.DeptDesigMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MappingRepository extends JpaRepository<DeptDesigMapping, UUID> {

    List<DeptDesigMapping> findByDeptId(UUID deptId);

    List<DeptDesigMapping> findByDesigId(UUID desigId);

    boolean existsByDeptIdAndDesigId(UUID deptId, UUID desigId);

}
