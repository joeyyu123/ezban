package com.ezban.eventcomment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;   

@Entity
@Table(name = "event_comment")
public class EventComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_comment_no", nullable = false)
	private Integer eventCommentNo;
	
	@NotNull
	@JoinColumn(name = "event_no", nullable = false)
	private Integer eventNo;
	
	@NotNull
	@JoinColumn(name = "member_no", nullable = false)
	private Integer memberNo;
	
	@Column(name = "event_comment_content")
	private String eventCommentContent;
	
	@Column(name = "event_comment_rate")
	private Integer eventCommentRate;
	
	@Column(name = "event_comment_time")
	private LocalDateTime eventCommentTime;
	
	@Column(name = "event_comment_status")
	private Byte eventCommentStatus;
	
	public EventComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventComment(Integer eventCommentNo, Integer eventNo, Integer memberNo, String eventCommentContent,
			Integer eventCommentRate, LocalDateTime eventCommentTime, Byte eventCommentStatus) {
		super();
		this.eventCommentNo = eventCommentNo;
		this.eventNo = eventNo;
		this.memberNo = memberNo;
		this.eventCommentContent = eventCommentContent;
		this.eventCommentRate = eventCommentRate;
		this.eventCommentTime = eventCommentTime;
		this.eventCommentStatus = eventCommentStatus;
	}

	public Integer getEventCommentNo() {
		return eventCommentNo;
	}

	public void setEventCommentNo(Integer eventCommentNo) {
		this.eventCommentNo = eventCommentNo;
	}

	public Integer getEventNo() {
		return eventNo;
	}

	public void setEventNo(Integer eventNo) {
		this.eventNo = eventNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public String getEventCommentContent() {
		return eventCommentContent;
	}

	public void setEventCommentContent(String eventCommentContent) {
		this.eventCommentContent = eventCommentContent;
	}

	public Integer getEventCommentRate() {
		return eventCommentRate;
	}

	public void setEventCommentRate(Integer eventCommentRate) {
		this.eventCommentRate = eventCommentRate;
	}

	public LocalDateTime getEventCommentTime() {
		return eventCommentTime;
	}

	public void setEventCommentTime(LocalDateTime eventCommentTime) {
		this.eventCommentTime = eventCommentTime;
	}

	public Byte getEventCommentStatus() {
		return eventCommentStatus;
	}

	public void setEventCommentStatus(Byte eventCommentStatus) {
		this.eventCommentStatus = eventCommentStatus;
	}

	@Override
	public String toString() {
		return "Event_comment [eventCommentNo=" + eventCommentNo + ", eventNo=" + eventNo + ", memberNo=" + memberNo
				+ ", eventCommentContent=" + eventCommentContent + ", eventCommentRate=" + eventCommentRate
				+ ", eventCommentTime=" + eventCommentTime + ", eventCommentStatus=" + eventCommentStatus + "]";
	}
	
	
}

