package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.services.SalaryPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryPdfController {

    private final SalaryPdfService pdfService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadSalaryPdf(
            @RequestParam UUID empId,
            @RequestParam Long month,
            @RequestParam Long year) {

        byte[] pdf = pdfService.generateSalaryPdf(empId, month, year);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=salary-slip.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
