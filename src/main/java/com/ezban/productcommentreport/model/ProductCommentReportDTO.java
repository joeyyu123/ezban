package com.ezban.productcommentreport.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ProductCommentReportDTO {
    private Integer productCommentReportNo;
    private Integer productCommentNo;
    private String productCommentContent;
    private Integer memberNo;
    private Integer adminNo;
    private String reportReason;
    private Instant reportDate;
    private Byte reportStatus;
    private String formattedReportDate;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").withZone(ZoneId.systemDefault());

    public ProductCommentReportDTO() {
        // Default constructor
    }

    public ProductCommentReportDTO(Integer productCommentReportNo, Integer productCommentNo, String productCommentContent, Integer memberNo, Integer adminNo, String reportReason, Instant reportDate, Byte reportStatus) {
        this.productCommentReportNo = productCommentReportNo;
        this.productCommentNo = productCommentNo;
        this.productCommentContent = productCommentContent;
        this.memberNo = memberNo;
        this.adminNo = adminNo;
        this.reportReason = reportReason;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
        this.formattedReportDate = formatter.format(reportDate);
    }

    // Getters 和 setters

    public Integer getProductCommentReportNo() {
        return productCommentReportNo;
    }

    public void setProductCommentReportNo(Integer productCommentReportNo) {
        this.productCommentReportNo = productCommentReportNo;
    }

    public Integer getProductCommentNo() {
        return productCommentNo;
    }

    public void setProductCommentNo(Integer productCommentNo) {
        this.productCommentNo = productCommentNo;
    }

    public String getProductCommentContent() {
        return productCommentContent;
    }

    public void setProductCommentContent(String productCommentContent) {
        this.productCommentContent = productCommentContent;
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
