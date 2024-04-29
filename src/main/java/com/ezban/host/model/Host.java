package com.ezban.host.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "host")
public class Host {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "host_no", nullable = false)
	private Integer hostNo;
	
	@Size(max = 20)
	@Column(name = "host_account", length = 20)
	private String hostAccount;
	
	@Size(max = 20)
	@Column(name = "host_pwd", length = 20)
	private String hostPwd;
	
	@Size(max = 50)
	@Column(name = "host_name", length = 50)
	private String hostName;
	
	@Size(max = 50)
	@Column(name = "host_mail", length = 50)
	private String hostMail;
	
	@Size(max = 15)
	@Column(name = "host_phone", length = 15)
	private String hostPhone;
	
	@Column(name = "host_status")
	private Byte hostStatus;
	
	public Host() {
		super();
	}

	public Host(Integer hostNo, String hostAccount, String hostPwd, String hostName, String hostMail, String hostPhone,
			Byte hostStatus) {
		super();
		this.hostNo = hostNo;
		this.hostAccount = hostAccount;
		this.hostPwd = hostPwd;
		this.hostName = hostName;
		this.hostMail = hostMail;
		this.hostPhone = hostPhone;
		this.hostStatus = hostStatus;
	}

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

	public Byte getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(Byte hostStatus) {
		this.hostStatus = hostStatus;
	}

	@Override
	public String toString() {
		return "Host [hostNo=" + hostNo + ", hostAccount=" + hostAccount + ", hostPwd=" + hostPwd + ", hostName="
				+ hostName + ", hostMail=" + hostMail + ", hostPhone=" + hostPhone + ", hostStatus=" + hostStatus + "]";
	}

}

