package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.RevisionTypeRequestDto;
import com.hrms.pms.pms_app.pms.dtos.RevisionTypeResponseDto;
import com.hrms.pms.pms_app.pms.services.RevisionTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/revision-types")
@RequiredArgsConstructor
public class RevisionTypeController {

    private final RevisionTypeService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RevisionTypeResponseDto create(
            @Valid @RequestBody RevisionTypeRequestDto dto) {
        return service.create(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public RevisionTypeResponseDto update(
            @PathVariable UUID id,
            @Valid @RequestBody RevisionTypeRequestDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public RevisionTypeResponseDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<RevisionTypeResponseDto> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deactivate(
            @PathVariable UUID id) {
        service.deactivate(id);
    }
}
