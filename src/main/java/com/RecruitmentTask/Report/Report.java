package com.RecruitmentTask.Report;

import com.RecruitmentTask.User.User;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataReport;

    @ManyToOne
    @JoinColumn(name = "reporting_person_id")
    private User reportingPerson;

    @Transient
    private Long reportingPersonId;

    private String reportUser;
    private String content;
    private String reportAddress;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataReport() {
        return dataReport;
    }

    public void setDataReport(LocalDate dataReport) {
        this.dataReport = dataReport;
    }

    public User getReportingPerson() {
        return reportingPerson;
    }

    public void setReportingPerson(User reportingPerson) {
        this.reportingPerson = reportingPerson;
    }

    public Long getReportingPersonId() {
        return reportingPersonId;
    }

    public void setReportingPersonId(Long reportingPersonId) {
        this.reportingPersonId = reportingPersonId;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", dataReport=" + dataReport +
                ", reportingPerson=" + reportingPerson +
                ", reportingPersonId=" + reportingPersonId + 
                ", reportUser='" + reportUser + '\'' +
                ", content='" + content + '\'' +
                ", reportAddress='" + reportAddress + '\'' +
                '}';
    }
}
