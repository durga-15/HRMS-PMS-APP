package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.TaxSlabRequestDto;
import com.hrms.pms.pms_app.pms.dtos.TaxSlabResponseDto;
import com.hrms.pms.pms_app.pms.entities.TaxSlab;
import com.hrms.pms.pms_app.pms.repositories.TaxSlabRepository;
import com.hrms.pms.pms_app.pms.services.TaxSlabService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxSlabServiceImpl implements TaxSlabService {

    private final TaxSlabRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public TaxSlabResponseDto create(TaxSlabRequestDto dto) {

        if (dto.getMinIncome() >= dto.getMaxIncome()) {
            throw new IllegalArgumentException("Invalid income range");
        }

        // Prevent overlap
        boolean exists = repository
                .existsByFinancialYearAndMinIncomeLessThanEqualAndMaxIncomeGreaterThanEqual(
                        dto.getFinancialYear(),
                        dto.getMaxIncome(),
                        dto.getMinIncome()
                );

        if (exists) {
            throw new IllegalArgumentException("Overlapping tax slab exists");
        }

        TaxSlab slab = modelMapper.map(dto, TaxSlab.class);
        slab.setIsActive(true);

        return modelMapper.map(repository.save(slab), TaxSlabResponseDto.class);
    }

    @Override
    public List<TaxSlabResponseDto> getAll(String financialYear) {
        return repository.findByFinancialYearAndIsActiveTrue(financialYear)
                .stream()
                .map(slab -> modelMapper.map(slab, TaxSlabResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaxSlabResponseDto getById(UUID id) {
        TaxSlab slab = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tax slab not found"));

        return modelMapper.map(slab, TaxSlabResponseDto.class);
    }

    @Override
    public TaxSlabResponseDto update(UUID id, TaxSlabRequestDto dto) {

        TaxSlab slab = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tax slab not found"));

        modelMapper.map(dto, slab);

        return modelMapper.map(repository.save(slab), TaxSlabResponseDto.class);
    }

    @Override
    public void deactivate(UUID id) {
        TaxSlab slab = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tax slab not found"));

//        slab.setIsActive(false);
        repository.delete(slab);
        repository.save(slab);
    }
}