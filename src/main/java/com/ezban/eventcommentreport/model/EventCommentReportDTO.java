package com.ezban.eventcommentreport.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EventCommentReportDTO {
    private Integer eventCommentReportNo;
    private Integer eventCommentNo;
    private String eventCommentContent;
    private Integer memberNo;
    private Integer adminNo;
    private String reportReason;
    private Instant reportDate;
    private Byte reportStatus;
    private String formattedReportDate;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").withZone(ZoneId.systemDefault());

    public EventCommentReportDTO() {
        // Default constructor
    }

    public EventCommentReportDTO(Integer eventCommentReportNo, Integer eventCommentNo, String eventCommentContent, Integer memberNo, Integer adminNo, String reportReason, Instant reportDate, Byte reportStatus) {
        this.eventCommentReportNo = eventCommentReportNo;
        this.eventCommentNo = eventCommentNo;
        this.eventCommentContent = eventCommentContent;
        this.memberNo = memberNo;
        this.adminNo = adminNo;
        this.reportReason = reportReason;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
        this.formattedReportDate = formatter.format(reportDate);
    }

    // Getters 和 setters

    public Integer getEventCommentReportNo() {
        return eventCommentReportNo;
    }

    public void setEventCommentReportNo(Integer eventCommentReportNo) {
        this.eventCommentReportNo = eventCommentReportNo;
    }

    public Integer getEventCommentNo() {
        return eventCommentNo;
    }

    public void setEventCommentNo(Integer eventCommentNo) {
        this.eventCommentNo = eventCommentNo;
    }

    public String getEventCommentContent() {
        return eventCommentContent;
    }

    public void setEventCommentContent(String eventCommentContent) {
        this.eventCommentContent = eventCommentContent;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Integer adminNo) {
        this.adminNo = adminNo;
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
        this.formattedReportDate = formatter.format(reportDate);
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getFormattedReportDate() {
        return formattedReportDate;
    }

    public void setFormattedReportDate(String formattedReportDate) {
        this.formattedReportDate = formattedReportDate;
    }
}
