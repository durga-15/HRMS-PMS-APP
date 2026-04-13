package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.DeptDesigMappingRequestDto;
import com.hrms.pms.pms_app.pms.dtos.DeptDesigMappingResponseDto;
import com.hrms.pms.pms_app.pms.entities.DeptDesigMapping;
import com.hrms.pms.pms_app.pms.repositories.MappingRepository;
import com.hrms.pms.pms_app.pms.services.DeptDesigMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeptDesigMappingServiceImpl implements DeptDesigMappingService {

    private final MappingRepository repository;

    @Override
    @Transactional
    public DeptDesigMappingResponseDto addMappings(DeptDesigMappingRequestDto requestDto) {

        UUID deptId = requestDto.getDeptId();
        UUID userId = requestDto.getUserId();

        List<DeptDesigMapping> mappings = requestDto.getDesigIds()
                .stream()
                .filter(desigId -> !repository.existsByDeptIdAndDesigId(deptId, desigId))
                .map(desigId -> DeptDesigMapping.builder()
                        .deptId(deptId)
                        .desigId(desigId)
                        .createdAt(Instant.now())
                        .createdBy(userId)
                        .build())
                .collect(Collectors.toList());

        repository.saveAll(mappings);

        return DeptDesigMappingResponseDto.builder()
                .message("mapping is done successfully")
                .build();
    }

    @Override
    public List<UUID> getDesignationsByDept(UUID deptId) {
        return repository.findByDeptId(deptId)
                .stream()
                .map(DeptDesigMapping::getDesigId)
                .collect(Collectors.toList());
    }

    @Override
    public DeptDesigMappingResponseDto deleteMapping(UUID deptId, UUID desigId) {

        List<DeptDesigMapping> mappings =
                repository.findByDeptId(deptId)
                        .stream()
                        .filter(m -> m.getDesigId().equals(desigId))
                        .collect(Collectors.toList());

        repository.deleteAll(mappings);

        return DeptDesigMappingResponseDto.builder()
                .message("mapping deleted successfully")
                .build();
    }

}

