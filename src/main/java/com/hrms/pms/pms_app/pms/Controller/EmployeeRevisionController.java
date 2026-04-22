package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionRequest;
import com.hrms.pms.pms_app.pms.dtos.EmployeeRevisionResponse;
import com.hrms.pms.pms_app.pms.services.EmployeeRevisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revisions")
@RequiredArgsConstructor
public class EmployeeRevisionController {

    private final EmployeeRevisionService revisionService;

    @PostMapping
    public ResponseEntity<EmployeeRevisionResponse> createRevision(
            @RequestBody @Valid EmployeeRevisionRequest request) {

        EmployeeRevisionResponse response = revisionService.processRevision(request);
        return ResponseEntity.ok(response);
    }
}
