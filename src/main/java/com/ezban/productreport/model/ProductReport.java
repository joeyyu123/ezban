package com.ezban.productreport.model;

import com.ezban.admin.model.Admin;
import com.ezban.member.model.Member;
import com.ezban.product.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "* 商品編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "admin_no")
    private Admin admin;

    @NotNull(message = "* 檢舉原因: 請勿空白 !")
    @Column(name = "report_reason", nullable = false, length = 100)
    private String reportReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @NotNull(message = "* 檢舉日期: 請勿空白 !")
    @Column(name = "report_date", nullable = false)
    private Timestamp reportDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "report_status", insertable = false)
    @Min(value = 0, message = "* 商品檢舉狀態不正確 !")
    @Max(value = 2, message = "* 商品檢舉狀態不正確 !")
    private Byte reportStatus;

    public ProductReport() {
    }

    public Integer getProductReportNo() {
        return productReportNo;
    }

    public void setProductReportNo(Integer productReportNo) {
        this.productReportNo = productReportNo;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Admin getAdmin() {
        return this.admin;
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