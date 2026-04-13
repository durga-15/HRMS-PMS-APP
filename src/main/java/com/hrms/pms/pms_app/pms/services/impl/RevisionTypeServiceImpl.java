package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.RevisionTypeRequestDto;
import com.hrms.pms.pms_app.pms.dtos.RevisionTypeResponseDto;
import com.hrms.pms.pms_app.pms.entities.RevisionType;
import com.hrms.pms.pms_app.pms.repositories.RevisionTypeRepository;
import com.hrms.pms.pms_app.pms.services.RevisionTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevisionTypeServiceImpl implements RevisionTypeService {

    private final RevisionTypeRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public RevisionTypeResponseDto create(RevisionTypeRequestDto dto) {

        if (repository.existsByRevisionNameIgnoreCase(dto.getRevisionName())) {
            throw new IllegalArgumentException("Revision type already exists");
        }

        RevisionType entity = modelMapper.map(dto, RevisionType.class);

        return modelMapper.map(repository.save(entity), RevisionTypeResponseDto.class);
    }

    @Override
    public RevisionTypeResponseDto update(UUID id, RevisionTypeRequestDto dto) {

        RevisionType entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revision type not found"));

        modelMapper.map(dto, entity);
        return modelMapper.map(repository.save(entity), RevisionTypeResponseDto.class);
    }

    @Override
    public RevisionTypeResponseDto getById(UUID id) {

        RevisionType entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revision type not found"));

        return modelMapper.map(entity, RevisionTypeResponseDto.class);
    }

    @Override
    public List<RevisionTypeResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, RevisionTypeResponseDto.class))
                .toList();
    }

    @Override
    public void deactivate(UUID id) {

        RevisionType entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revision type not found"));

        entity.setIsActive(false);

        repository.save(entity);
    }
}
