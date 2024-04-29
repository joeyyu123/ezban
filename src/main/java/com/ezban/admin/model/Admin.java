package com.ezban.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "admin")
public class Admin implements java.io.Serializable {
   

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no", nullable = false)
    private Integer adminNo;

    @Column(name = "admin_account", nullable = false, length = 20)
    @NotNull
    private String adminAccount;

    @Column(name = "admin_pwd", nullable = false, length = 20)
    @NotNull
    private String adminPwd;

    @Column(name = "admin_name", nullable = false, length = 50)
    @NotNull
    private String adminName;

    @Column(name = "admin_mail", nullable = false, length = 50)
    @NotNull
    private String adminMail;

    @Column(name = "admin_phone", nullable = false, length = 15)
    @NotNull
    private String adminPhone;

    @Column(name = "admin_status", nullable = false)
    @NotNull
    private Byte adminStatus;

    // Getters and Setters
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

}
