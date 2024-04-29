package com.ezban.product.comment.report.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product_comment_report", schema = "ezban")
public class ProductCommentRreport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_report_no", nullable = false)
    private Integer productCommentReportNo;

    @Column(name = "product_comment_no")
    private Integer productCommentNo;

    @Column(name = "member_no")
    private Integer memberNo;

    @Column(name = "admin_no")
    private Integer adminNo;

    @Column(name = "report_reason", length = 100)
    private String reportReason;

    @Column(name = "report_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;

    @Column(name = "report_status")
    private Byte reportStatus;

    
    public ProductCommentRreport() {}

    
    public ProductCommentRreport(Integer productCommentReportNo, Integer productCommentNo, Integer memberNo, Integer adminNo, String reportReason, Date reportDate, Byte reportStatus) {
        this.productCommentReportNo = productCommentReportNo;
        this.productCommentNo = productCommentNo;
        this.memberNo = memberNo;
        this.adminNo = adminNo;
        this.reportReason = reportReason;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    
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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public String toString() {
        return "ProductCommentReportVO{" +
                "productCommentReportNo=" + productCommentReportNo +
                ", productCommentNo=" + productCommentNo +
                ", memberNo=" + memberNo +
                ", adminNo=" + adminNo +
                ", reportReason='" + reportReason + '\'' +
                ", reportDate=" + reportDate +
                ", reportStatus=" + reportStatus +
                '}';
    }

}
