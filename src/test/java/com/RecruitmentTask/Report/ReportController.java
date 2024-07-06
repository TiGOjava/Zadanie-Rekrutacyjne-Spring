package com.RecruitmentTask.Report;

import com.RecruitmentTask.User.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private Model model;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReports() {
        Report report = new Report();
        List<Report> reports = Collections.singletonList(report);
        when(reportService.findAll()).thenReturn(reports);

        ResponseEntity<List<Report>> response = reportController.getAllReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
    }

    @Test
    void testGetReportById() {
        Report report = new Report();
        when(reportService.findById(anyLong())).thenReturn(report);

        ResponseEntity<Report> response = reportController.getReportById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testEditReportPage() {
        Report report = new Report();
        when(reportService.findById(anyLong())).thenReturn(report);

        String viewName = reportController.editReportPage(1L, model);

        assertEquals("edit-report", viewName);
        verify(model, times(1)).addAttribute("report", report);
    }

    @Test
    void testCreateReport() {
        User user = new User();
        user.setId(1L);
        Report report = new Report();
        report.setReportingPerson(user);
        Report savedReport = new Report();
        savedReport.setId(1L);

        when(reportService.saveReport(any(Report.class), anyLong())).thenReturn(savedReport);

        RedirectView redirectView = reportController.createReport(report);

        assertEquals("/home", redirectView.getUrl());
    }

    @Test
    void testUpdateReport() {
        Report updatedReport = new Report();
        when(reportService.updateReport(anyLong(), anyString(), anyString(), anyString())).thenReturn(updatedReport);

        String viewName = reportController.updateReport(1L, "user", "content", "address", model);

        assertEquals("redirect:/home", viewName);
        verify(model, times(1)).addAttribute("report", updatedReport);
    }

    @Test
    void testDeleteReport() {
        doNothing().when(reportService).deleteById(anyLong());

        RedirectView redirectView = reportController.deleteReport(1L);

        assertEquals("/home", redirectView.getUrl());
    }

    @Test
    void testDeleteReportWithException() {
        doThrow(new EntityNotFoundException("Report not found")).when(reportService).deleteById(anyLong());

        RedirectView redirectView = reportController.deleteReport(1L);

        assertEquals("/home?error=Report not found", redirectView.getUrl());
    }
}
