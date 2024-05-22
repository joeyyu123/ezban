package com.ezban.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ezban.authority.model.Authority;
import com.ezban.eventcommentreport.model.EventCommentReport;
import com.ezban.productcommentreport.model.ProductCommentReport;
import com.ezban.productreport.model.ProductReport;

@Entity
@Table(name = "admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no", nullable = false)
    private Integer adminNo;

    @Size(max = 20)
    @NotNull
    @Column(name = "admin_account", nullable = false, length = 20)
    private String adminAccount;

    @Size(max = 60)
    @Column(name = "admin_pwd", nullable = false, length = 20)
    private String adminPwd;

    @Size(max = 50)
    @Column(name = "admin_name", length = 50)
    private String adminName;

    @Size(max = 50)
    @Column(name = "admin_mail", length = 50)
    private String adminMail;

    @Size(max = 15)
    @Column(name = "admin_phone", length = 15)
    private String adminPhone;

    @Column(name = "admin_status")
    private Byte adminStatus = 1;
    
    @Column(name = "admin_login")
    private LocalDateTime adminLogin;
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Authority> authorities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EventCommentReport> eventCommentReports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductCommentReport> productCommentReports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductReport> productReports = new LinkedHashSet<>();

    public Integer getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Integer adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public Byte getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Byte adminStatus) {
        this.adminStatus = adminStatus;
    }

    public LocalDateTime getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(LocalDateTime adminLogin) {
        this.adminLogin = adminLogin;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<EventCommentReport> getEventCommentReports() {
        return eventCommentReports;
    }

    public void setEventCommentReports(Set<EventCommentReport> eventCommentReports) {
        this.eventCommentReports = eventCommentReports;
    }

    public Set<ProductCommentReport> getProductCommentReports() {
        return productCommentReports;
    }

    public void setProductCommentReports(Set<ProductCommentReport> productCommentReports) {
        this.productCommentReports = productCommentReports;
    }

    public Set<ProductReport> getProductReports() {
        return productReports;
    }

    public void setProductReports(Set<ProductReport> productReports) {
        this.productReports = productReports;
    }
}
