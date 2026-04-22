package com.hrms.pms.pms_app.pms.repositories;

import com.hrms.pms.pms_app.pms.entities.RevisionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RevisionTypeRepository extends JpaRepository<RevisionType, UUID> {

    boolean existsByRevisionNameIgnoreCase(String revisionName);

    boolean existsByRevisionNameIgnoreCaseAndIdNot(String revisionName, UUID id);

    Optional<RevisionType> findByRevisionNameIgnoreCase(String revisionName);

    Optional<RevisionType> findByIdAndIsActiveTrue(UUID id);
}
