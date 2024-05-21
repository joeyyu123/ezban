package com.ezban.notification.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.ezban.admin.model.Admin;
import com.ezban.host.model.Host;
import com.ezban.member.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.time.LocalDateTime; 


@Entity
@Table(name = "notification")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_no", nullable = false)
	private Integer notificationNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_no")
	private Host host;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_no")
	private Admin admin;
	
	@Size(max = 200)
	@Column(name = "notification_content", length = 200)
	private String notificationContent;
	
	@Column(name = "read_status")
	private Byte readStatus;
	
	@Column(name = "notification_time")
	private Timestamp notificationTime;
	
	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notification(Integer notificationNo, Member member, Host host, Admin admin,
			@Size(max = 200) String notificationContent, Byte readStatus, Timestamp notificationTime) {
		super();
		this.notificationNo = notificationNo;
		this.member = member;
		this.host = host;
		this.admin = admin;
		this.notificationContent = notificationContent;
		this.readStatus = readStatus;
		this.notificationTime = notificationTime;
	}

	public Integer getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(Integer notificationNo) {
		this.notificationNo = notificationNo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getNotificationContent() {
		return notificationContent;
	}

	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}

	public Byte getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Byte readStatus) {
		this.readStatus = readStatus;
	}

	public Timestamp getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Timestamp notificationTime) {
		this.notificationTime = notificationTime;
	}

	@Override
	public String toString() {
		return "Notification [notificationNo=" + notificationNo + ", member=" + member + ", host=" + host
				+ ", notificationContent=" + notificationContent + ", readStatus=" + readStatus + ", notificationTime="
				+ notificationTime + "]";
	}
	
	
}

