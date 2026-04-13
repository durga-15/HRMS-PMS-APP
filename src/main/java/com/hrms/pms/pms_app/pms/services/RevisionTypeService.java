package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.RevisionTypeRequestDto;
import com.hrms.pms.pms_app.pms.dtos.RevisionTypeResponseDto;

import java.util.List;
import java.util.UUID;

public interface RevisionTypeService {

    RevisionTypeResponseDto create(RevisionTypeRequestDto dto);

    RevisionTypeResponseDto update(UUID id, RevisionTypeRequestDto dto);

    RevisionTypeResponseDto getById(UUID id);

    List<RevisionTypeResponseDto> getAll();

    void deactivate(UUID id);
}
