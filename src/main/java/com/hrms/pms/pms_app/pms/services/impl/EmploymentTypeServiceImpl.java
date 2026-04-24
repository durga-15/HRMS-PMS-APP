package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.EmploymentTypeDto;
import com.hrms.pms.pms_app.pms.entities.EmploymentType;
import com.hrms.pms.pms_app.pms.repositories.EmploymentTypeRepository;
import com.hrms.pms.pms_app.pms.services.EmploymentTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentTypeServiceImpl implements EmploymentTypeService {

    private final EmploymentTypeRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public EmploymentTypeDto createEmploymentType(EmploymentTypeDto dto) {
        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(e -> { throw new IllegalArgumentException("Employment Type already exists: " + dto.getName()); });

        EmploymentType entity = modelMapper.map(dto, EmploymentType.class);
        if (entity.getIsActive() == null) entity.setIsActive(true);

        EmploymentType saved = repository.save(entity);
        return modelMapper.map(saved, EmploymentTypeDto.class);
    }

    @Override
    public List<EmploymentTypeDto> getAllEmploymentTypes() {
        return repository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmploymentTypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmploymentTypeDto getEmploymentTypeById(UUID id) {
        EmploymentType entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employment Type not found"));
        return modelMapper.map(entity, EmploymentTypeDto.class);
    }

    @Override
    @Transactional
    public EmploymentTypeDto updateEmploymentType(UUID id, EmploymentTypeDto dto) {
        EmploymentType existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employment Type not found"));

        existing.setName(dto.getName());
//        existing.setDescription(dto.getDescription());
        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        EmploymentType updated = repository.save(existing);
        return modelMapper.map(updated, EmploymentTypeDto.class);
    }

    @Override
    @Transactional
    public void deleteEmploymentType(UUID id) {
        EmploymentType existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employment Type not found"));
        repository.delete(existing);
    }
}

