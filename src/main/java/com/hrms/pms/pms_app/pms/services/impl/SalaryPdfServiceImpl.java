package com.hrms.pms.pms_app.pms.services.impl;

import com.hrms.pms.pms_app.pms.dtos.ComponentDto;
import com.hrms.pms.pms_app.pms.dtos.EmployeeSalaryResponseDto;
import com.hrms.pms.pms_app.pms.services.EmployeeSalaryService;
import com.hrms.pms.pms_app.pms.services.SalaryPdfService;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class SalaryPdfServiceImpl implements SalaryPdfService {
//
//    private final EmployeeSalaryService salaryService;
//
//    @Override
//    public byte[] generateSalaryPdf(UUID empId, Long month, Long year) {
//
//        EmployeeSalaryResponseDto salary =
//                salaryService.getSalary(empId, month, year);
//
//        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            PdfWriter writer = new PdfWriter(out);
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document document = new Document(pdfDoc);
//
//            // Title
//            document.add(new Paragraph("Employee Salary Slip")
//                    .setBold()
//                    .setFontSize(16));
//
//            // Basic Info
//            document.add(new Paragraph("Employee ID: " + salary.getEmpId()));
//            document.add(new Paragraph("Month: " + month + " Year: " + year));
//
//            document.add(new Paragraph("\n"));
//
//            // Salary Summary
//            document.add(new Paragraph("Gross Salary: " + salary.getGrossSalary()));
//            document.add(new Paragraph("Total Deductions: " + salary.getTotalDeductions()));
//            document.add(new Paragraph("Net Salary: " + salary.getNetSalary()));
//
//            document.add(new Paragraph("\n"));
//
//            // Components Table
//            Table table = new Table(3);
//            table.addHeaderCell("Component");
//            table.addHeaderCell("Type");
//            table.addHeaderCell("Amount");
////            table.addHeaderCell("Component ID");
//
//            for (ComponentDto comp : salary.getComponents()) {
//                table.addCell(comp.getCompName());
//                table.addCell(String.valueOf(comp.getCompType()));
//                table.addCell(String.valueOf(comp.getAmount()));
////                table.addCell(String.valueOf(comp.getCompId()));
//            }
//
//            document.add(table);
//
//            document.close();
//
//            return out.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("Error while generating PDF", e);
//        }
//    }
//}

@Service
@RequiredArgsConstructor
public class SalaryPdfServiceImpl implements SalaryPdfService {

    private final EmployeeSalaryService salaryService;

    @Override
    public byte[] generateSalaryPdf(UUID empId, Long month, Long year) {

        EmployeeSalaryResponseDto salary =
                salaryService.getSalary(empId, month, year);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // ================= HEADER =================
            document.add(new Paragraph("VVDN Technologies Pvt Ltd")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            document.add(new Paragraph("Salary Slip")
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

//            document.add(new Paragraph("Month: " + month + " / " + year)
//                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // ================= EMPLOYEE DETAILS =================
            Table empTable = new Table(2).useAllAvailableWidth();

//            empTable.addCell("Employee Name");
//            empTable.addCell(salary.getEmployeeName());  // ✅ now comes from service

            empTable.addCell("Employee ID");
            empTable.addCell(String.valueOf(salary.getEmpId()));

            empTable.addCell("Pay Period");
            empTable.addCell(month + "/" + year);

            document.add(empTable);

            document.add(new Paragraph("\n"));

            // ================= SPLIT COMPONENTS =================
            List<ComponentDto> earnings = salary.getComponents().stream()
                    .filter(c -> "EARNING".equalsIgnoreCase(c.getCompType()))
                    .toList();

            List<ComponentDto> deductions = salary.getComponents().stream()
                    .filter(c -> "DEDUCTION".equalsIgnoreCase(c.getCompType()))
                    .toList();

            // ================= EARNINGS =================
            document.add(new Paragraph("Earnings").setBold());

            Table earnTable = new Table(2).useAllAvailableWidth();
            earnTable.addHeaderCell("Component");
            earnTable.addHeaderCell("Amount");

            for (ComponentDto comp : earnings) {
                earnTable.addCell(comp.getCompName());
                earnTable.addCell("₹ " + comp.getAmount());
            }

            document.add(earnTable);

            document.add(new Paragraph("\n"));

            // ================= DEDUCTIONS =================
            document.add(new Paragraph("Deductions").setBold());

            Table dedTable = new Table(2).useAllAvailableWidth();
            dedTable.addHeaderCell("Component");
            dedTable.addHeaderCell("Amount");

            for (ComponentDto comp : deductions) {
                dedTable.addCell(comp.getCompName());
                dedTable.addCell("₹ " + comp.getAmount());
            }

            document.add(dedTable);

            document.add(new Paragraph("\n"));

            // ================= SUMMARY =================
            document.add(new Paragraph("Summary").setBold());

            Table summary = new Table(2).useAllAvailableWidth();

            summary.addCell("Gross Salary");
            summary.addCell("₹ " + salary.getGrossSalary());

            summary.addCell("Total Deductions");
            summary.addCell("₹ " + salary.getTotalDeductions());

            summary.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Net Salary").setBold()));

            summary.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("₹ " + salary.getNetSalary()).setBold()));

            document.add(summary);

            document.add(new Paragraph("\n"));

            // ================= FOOTER =================
            document.add(new Paragraph("This is a system-generated payslip.")
                    .setFontSize(9)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error while generating PDF", e);
        }
    }
}