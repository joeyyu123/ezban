package com.ezban.host.model;

import java.time.LocalDateTime;
import com.ezban.product.model.Product;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;



@Entity
@Table(name = "host")
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

    @Column(name = "host_login")
    private LocalDateTime hostlogin;

    @Column(name = "host_status", nullable = false)
    private Byte hostStatus = 1;
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
	private Set<Product> products = new LinkedHashSet<>();

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
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
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

    public LocalDateTime getLastLogin() {
        return hostlogin;
    }

    public void sethostlogin(LocalDateTime hostlogin) {
        this.hostlogin = hostlogin;
    }

    public Byte getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(Byte hostStatus) {
        this.hostStatus = hostStatus;
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
                ", lastLogin=" + hostlogin +
                ", hostStatus=" + hostStatus +
                '}';
    }
}
