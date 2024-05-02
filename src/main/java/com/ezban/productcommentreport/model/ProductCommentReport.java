package com.ezban.productcommentreport.model;

import com.ezban.admin.model.Admin;
import com.ezban.member.model.Member;
import com.ezban.productcomment.model.ProductComment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "product_comment_report", schema = "ezban")
public class ProductCommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_report_no", nullable = false)
    private Integer productCommentReportNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_comment_no", nullable = false)
    private ProductComment productCommentNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_no")
    private Admin admin;

    @Size(max = 100)
    @NotNull
    @Column(name = "report_reason", nullable = false, length = 100)
    private String reportReason;

    @NotNull
    @Column(name = "report_date", nullable = false)
    private Instant reportDate;

    @NotNull
    @Column(name = "report_status", nullable = false)
    private Byte reportStatus;

    public Integer getProductCommentReportNo() {
        return productCommentReportNo;
    }

    public void setProductCommentReportNo(Integer productCommentReportNo) {
        this.productCommentReportNo = productCommentReportNo;
    }

    public ProductComment getProductCommentNo() {
        return productCommentNo;
    }

    public void setProductCommentNo(ProductComment productCommentNo) {
        this.productCommentNo = productCommentNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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

}