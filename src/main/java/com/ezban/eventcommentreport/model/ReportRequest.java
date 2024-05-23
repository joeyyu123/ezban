package com.ezban.eventcommentreport.model;

import java.time.Instant;

public class ReportRequest {
    private Integer eventCommentNo;
    private Integer memberNo;
    private String reportReason;
    private Instant reportDate;
    private Byte reportStatus;
    private Integer adminNo;

    // Getters and Setters
    public Integer getEventCommentNo() {
        return eventCommentNo;
    }

    public void setEventCommentNo(Integer eventCommentNo) {
        this.eventCommentNo = eventCommentNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public Instant getReportDate() {
        return reportDate;
    }

    public void setReportDate(Instant reportDate) {
        this.reportDate = reportDate;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Integer adminNo) {
        this.adminNo = adminNo;
    }
}
