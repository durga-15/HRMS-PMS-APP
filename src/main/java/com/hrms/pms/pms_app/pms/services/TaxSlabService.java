package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.TaxSlabRequestDto;
import com.hrms.pms.pms_app.pms.dtos.TaxSlabResponseDto;

import java.util.List;
import java.util.UUID;

public interface TaxSlabService {

    TaxSlabResponseDto create(TaxSlabRequestDto dto);

    List<TaxSlabResponseDto> getAll(String financialYear);

    TaxSlabResponseDto getById(UUID id);

    TaxSlabResponseDto update(UUID id, TaxSlabRequestDto dto);

    void deactivate(UUID id);
}