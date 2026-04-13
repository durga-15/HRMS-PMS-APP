package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.TaxSlabRequestDto;
import com.hrms.pms.pms_app.pms.dtos.TaxSlabResponseDto;
import com.hrms.pms.pms_app.pms.services.TaxSlabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tax-slabs")
@RequiredArgsConstructor
public class TaxSlabController {

    private final TaxSlabService service;

    @PostMapping
    public TaxSlabResponseDto create(@Valid @RequestBody TaxSlabRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<TaxSlabResponseDto> getAll(@RequestParam String financialYear) {
        return service.getAll(financialYear);
    }

    @GetMapping("/{id}")
    public TaxSlabResponseDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public TaxSlabResponseDto update(@PathVariable UUID id,
                                     @Valid @RequestBody TaxSlabRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
