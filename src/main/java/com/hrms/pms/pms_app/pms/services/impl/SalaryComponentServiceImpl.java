package com.hrms.pms.pms_app.pms.services.impl;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentDto;
import com.hrms.pms.pms_app.pms.dtos.SalaryComponentResponseDto;
import com.hrms.pms.pms_app.pms.entities.SalaryComponent;
import com.hrms.pms.pms_app.pms.exception.ResourceNotFoundException;
import com.hrms.pms.pms_app.pms.repositories.SalaryComponentRepository;
import com.hrms.pms.pms_app.pms.services.SalaryComponentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryComponentServiceImpl implements SalaryComponentService {

    private final SalaryComponentRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SalaryComponentResponseDto createSalaryComponent(SalaryComponentDto dto) {

        // Check duplicate name
        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(sc -> {
                    throw new IllegalArgumentException("Salary Component already exists with name: " + dto.getName());
                });

        // Map DTO → Entity
        SalaryComponent entity = modelMapper.map(dto, SalaryComponent.class);

        // Default value for isActive if null
        if (entity.getIsActive() == null) entity.setIsActive(true);

        // Save entity
        SalaryComponent saved = repository.save(entity);

        // Map Entity → DTO
//        return modelMapper.map(saved, SalaryComponentDto.class);
        return SalaryComponentResponseDto.builder()
                .message("Salary component created successfully")
                .build();
    }



    @Override
    public List<SalaryComponentDto> getAllSalaryComponents() {
        return repository.findAll()
                .stream()
                .map(sc -> modelMapper.map(sc, SalaryComponentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SalaryComponentDto getSalaryComponentById(String id) {
        SalaryComponent sc = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Salary Component not found with ID: " + id));
        return modelMapper.map(sc, SalaryComponentDto.class);
    }

    @Override
    @Transactional
    public SalaryComponentResponseDto updateSalaryComponent(String id, SalaryComponentDto dto) {
        SalaryComponent existing = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Salary Component not found with ID: " + id));

        // Optional: check if name is being updated to a duplicate
        if (!existing.getName().equalsIgnoreCase(dto.getName().trim())) {
            repository.findByNameIgnoreCase(dto.getName().trim())
                    .ifPresent(sc -> {
                        throw new IllegalArgumentException("Salary Component already exists with name: " + dto.getName());
                    });
        }

        // Map updated fields from DTO to entity (ignoring read-only fields)
        existing.setName(dto.getName().trim());
        existing.setType(dto.getType());
        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

//        SalaryComponent updated = repository.save(existing);

//        return modelMapper.map(updated, SalaryComponentDto.class);
        return SalaryComponentResponseDto.builder()
                .message("Salary component updated successfully")
                .build();
    }

//    @Override
//    @Transactional
//    public void deleteSalaryComponent(String id) {
//        SalaryComponent existing = repository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new ResourceNotFoundException("Salary Component not found with ID: " + id));
//        repository.delete(existing);
//    }
    @Override
    @Transactional
    public SalaryComponentResponseDto deleteSalaryComponent(String id) {

        SalaryComponent existing = repository.findById(UUID.fromString(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Salary Component not found with ID: " + id));

        repository.delete(existing);

        return SalaryComponentResponseDto.builder()
                .message("Salary component deleted successfully")
                .build();
    }
}