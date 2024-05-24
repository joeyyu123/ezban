package com.ezban.host.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ezban.admin.model.Admin;
import com.ezban.eventcoupon.model.EventCoupon;
import com.ezban.product.model.Product;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "host")
@JsonIgnoreProperties({"products", "eventCoupons"})
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_no", nullable = false)
    private Integer hostNo;

    @NotBlank(message = "帳號不能為空")
    @Size(max = 20, message = "帳號最大長度為 20")
    @Column(name = "host_account", length = 20, unique = true, nullable = false)
    private String hostAccount;

    @Size(max = 60, message = "密碼最大長度為 60")
    @Column(name = "host_pwd", length = 60)
    private String hostPwd;

    @NotBlank(message = "名稱不能為空")
    @Size(max = 50, message = "名稱最大長度為 50")
    @Column(name = "host_name", length = 50, unique = true, nullable = false)
    private String hostName;

    @NotBlank(message = "電子郵件不能為空")
    @Email(message = "無效的電子郵件格式")
    @Column(name = "host_mail", length = 50, unique = true, nullable = false)
    private String hostMail;

    @NotBlank(message = "電話不能為空")
    @Pattern(regexp = "\\d{10}", message = "電話必須是 10 位數字")
    @Column(name = "host_phone", length = 15, unique = true, nullable = false)
    private String hostPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "host_login")
    private LocalDateTime hostLogin;

    @Column(name = "host_status", nullable = false)
    private Byte hostStatus = 1;

    @JsonManagedReference
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "host")
    @OrderBy("eventCouponNo asc")
    @JsonIgnore
    private Set<EventCoupon> eventCoupons = new HashSet<EventCoupon>();


    // Default constructor
    public Host() {}

    // Constructor with parameters
    public Host(Integer hostNo, String hostAccount, String hostPwd, String hostName, String hostMail, String hostPhone,
                Byte hostStatus) {
        this.hostNo = hostNo;
        this.hostAccount = hostAccount;
        this.hostPwd = hostPwd;
        this.hostName = hostName;
        this.hostMail = hostMail;
        this.hostPhone = hostPhone;
        this.hostStatus = hostStatus;
    }

    // Getters and setters
    public Integer getHostNo() {
        return hostNo;
    }

    public void setHostNo(Integer hostNo) {
        this.hostNo = hostNo;
    }

    public String getHostAccount() {
        return hostAccount;
    }

    public void setHostAccount(String hostAccount) {
        this.hostAccount = hostAccount;
    }

    public String getHostPwd() {
        return hostPwd;
    }

    public void setHostPwd(String hostPwd) {
        this.hostPwd = hostPwd;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostMail() {
        return hostMail;
    }

    public void setHostMail(String hostMail) {
        this.hostMail = hostMail;
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone;
    }

    public LocalDateTime getHostLogin() {
        return hostLogin;
    }

    public void setHostLogin(LocalDateTime hostLogin) {
        this.hostLogin = hostLogin;
    }

    public Byte getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(Byte hostStatus) {
        this.hostStatus = hostStatus;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<EventCoupon> getEventCoupons() {
        return eventCoupons;
    }

    public void setEventCoupons(Set<EventCoupon> eventCoupons) {
        this.eventCoupons = eventCoupons;
    }

    @Override
    public String toString() {
        return "Host{" +
                "hostNo=" + hostNo +
                ", hostAccount='" + hostAccount + '\'' +
                ", hostPwd='" + hostPwd + '\'' +
                ", hostName='" + hostName + '\'' +
                ", hostMail='" + hostMail + '\'' +
                ", hostPhone='" + hostPhone + '\'' +
                ", hostLogin=" + hostLogin +
                ", hostStatus=" + hostStatus +
                '}';
    }
}
