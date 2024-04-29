package com.ezban.productreport.model;

import com.ezban.saveproduct.model.Member;
import com.ezban.saveproduct.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_report", schema = "ezban")
public class ProductReport implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_report_no", nullable = false)
    private Integer productReportNo;

    @NotNull(message = "商品編號: 請勿空白")
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @NotNull(message = "會員編號: 請勿空白")
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @NotNull(message = "後台管理員編號: 請勿空白")
    @ManyToOne
    @JoinColumn(name = "admin_no", nullable = false)
    private Admin admin;

    @NotNull(message = "檢舉原因: 請勿空白")
    @Column(name = "report_reason", nullable = false, length = 100)
    private String reportReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @NotNull(message = "檢舉日期: 請勿空白")
    @Column(name = "report_date", nullable = false)
    private Timestamp reportDate = Timestamp.valueOf(LocalDateTime.now());

    @NotNull(message = "商品檢舉狀態: 請勿空白")
    @Column(name = "report_status", nullable = false)
    @Pattern(regexp = "^[0-2]$", message = "商品檢舉狀態不正確")
    private Byte reportStatus;

    public ProductReport() {
    }

    public Integer getProductReportNo() {
        return productReportNo;
    }

    public void setProductReportNo(Integer productReportNo) {
        this.productReportNo = productReportNo;
    }

    public Product getProductVO() {
        return this.product;
    }

    public void setProductVO(Product product) {
        this.product = product;
    }

    public Member getMemberVO() {
        return this.member;
    }

    public void setMemberVO(Member member) {
        this.member = member;
    }

    public Admin getAdminVO() {
        return this.admin;
    }

    public void setAdminVO(Admin admin) {
        this.admin = admin;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate = reportDate;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }
}