package com.RecruitmentTask.Report;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.findAll();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Report report = reportService.findById(id);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/edit-report/{id}")
    public String editReportPage(@PathVariable Long id, Model model) {
        Report report = reportService.findById(id);
        if (report != null) {
            model.addAttribute("report", report);
            return "edit-report";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping
    public RedirectView createReport(@RequestBody Report report) {
        logger.info("Received report data: {}", report);
        Report savedReport = reportService.saveReport(report, report.getReportingPerson().getId());
        logger.info("Saved report data: {}", savedReport);
        return new RedirectView("/home");
    }

    @PutMapping("/edit/{id}")
    public String updateReport(@PathVariable Long id,
                               @RequestParam(required = false) String reportUser,
                               @RequestParam(required = false) String content,
                               @RequestParam(required = false) String reportAddress,
                               Model model) {
        logger.info("Updating report with ID: {}", id);
        Report updatedReport = reportService.updateReport(id, reportUser, content, reportAddress);
        logger.info("Updated report data: {}", updatedReport);
        model.addAttribute("report", updatedReport);
        return "redirect:/home";
    }

    @DeleteMapping("/delete/{id}")
    public RedirectView deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteById(id);
            return new RedirectView("/home");
        } catch (EntityNotFoundException ex) {
            logger.error("Failed to delete report with ID {}: {}", id, ex.getMessage());
            return new RedirectView("/home?error=Report not found");
        }
    }
}
