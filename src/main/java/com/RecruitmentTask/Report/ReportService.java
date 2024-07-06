package com.RecruitmentTask.Report;

import com.RecruitmentTask.User.User;
import com.RecruitmentTask.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Report not found"));
    }

    public Report save(Report report) {
        if (report.getId() != null && reportRepository.existsById(report.getId())) {
            return reportRepository.save(report);
        } else {
            return reportRepository.save(report);
        }
    }

    public Report saveReport(Report report, Long userId) {
        logger.info("Saving report for user ID: {}", userId); 
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            report.setReportingPerson(user);
            Report savedReport = reportRepository.save(report);
            logger.info("Report saved: {}", savedReport); 
            return savedReport;
        } else {
            logger.error("User with id {} not found.", userId); 
            throw new EntityNotFoundException("User with id " + userId + " not found.");
        }
    }

    public Report updateReport(Long id, String reportUser, String content, String reportAddress) {
        logger.info("Updating report with ID: {}", id); 
        Report report = reportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Report not found"));

        if (reportUser != null) {
            report.setReportUser(reportUser);
        }
        if (content != null) {
            report.setContent(content);
        }
        if (reportAddress != null) {
            report.setReportAddress(reportAddress);
        }

        Report updatedReport = reportRepository.save(report);
        logger.info("Updated report: {}", updatedReport); 
        return updatedReport;
    }

    public void deleteById(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EntityNotFoundException("Report with id " + id + " not found.");
        }
        reportRepository.deleteById(id);
    }

    public List<Report> findByReportingPersonUsername(String username) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
        return reportRepository.findByReportingPerson(user);
    }
}
