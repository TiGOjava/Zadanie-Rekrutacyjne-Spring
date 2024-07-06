package com.RecruitmentTask.Report;

import com.RecruitmentTask.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportingPerson(User reportingPerson);
}
