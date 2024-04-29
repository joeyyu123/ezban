package com.ezban.notification.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.time.LocalDateTime; 


@Entity
@Table(name = "notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_no", nullable = false)
	private Integer notificationNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Integer memberNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_no")
	private Integer hostNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_no")
	private Integer adminNo;
	
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

	public Notification(Integer notificationNo, Integer memberNo, Integer hostNo, Integer adminNo,
			String notificationContent, Byte readStatus, Timestamp notificationTime) {
		super();
		this.notificationNo = notificationNo;
		this.memberNo = memberNo;
		this.hostNo = hostNo;
		this.adminNo = adminNo;
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

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getHostNo() {
		return hostNo;
	}

	public void setHostNo(Integer hostNo) {
		this.hostNo = hostNo;
	}

	public Integer getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
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
		return "Notification [notificationNo=" + notificationNo + ", memberNo=" + memberNo + ", hostNo=" + hostNo
				+ ", adminNo=" + adminNo + ", notificationContent=" + notificationContent + ", readStatus=" + readStatus
				+ ", notificationTime=" + notificationTime + "]";
	}
	
	
}

