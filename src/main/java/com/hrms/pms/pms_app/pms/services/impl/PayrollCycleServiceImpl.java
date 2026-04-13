package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.PayrollCycleRequestDto;
import com.hrms.pms.pms_app.pms.dtos.PayrollCycleResponseDto;
import com.hrms.pms.pms_app.pms.entities.PayrollCycle;
import com.hrms.pms.pms_app.pms.repositories.PayrollCycleRepository;
import com.hrms.pms.pms_app.pms.services.PayrollCycleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollCycleServiceImpl implements PayrollCycleService {

    private final PayrollCycleRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public PayrollCycleResponseDto create(PayrollCycleRequestDto dto) {

        if (repository.existsByCycleName(dto.getCycleName())) {
            throw new RuntimeException("Cycle name already exists");
        }

        PayrollCycle entity = modelMapper.map(dto, PayrollCycle.class);

        PayrollCycle saved = repository.save(entity);

        return modelMapper.map(saved, PayrollCycleResponseDto.class);
    }

    @Override
    public PayrollCycleResponseDto getById(UUID id) {
        PayrollCycle entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll Cycle not found"));

        return modelMapper.map(entity, PayrollCycleResponseDto.class);
    }

    @Override
    public List<PayrollCycleResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, PayrollCycleResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PayrollCycleResponseDto update(UUID id, PayrollCycleRequestDto dto) {

        PayrollCycle existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll Cycle not found"));

        modelMapper.map(dto, existing);

        PayrollCycle updated = repository.save(existing);

        return modelMapper.map(updated, PayrollCycleResponseDto.class);
    }

    @Override
    public void delete(UUID id) {
        PayrollCycle existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll Cycle not found"));

        repository.delete(existing);
    }
}
