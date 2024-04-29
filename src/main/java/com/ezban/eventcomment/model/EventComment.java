package com.ezban.eventcomment.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ezban.event.model.Event;

import java.sql.Timestamp; 

@Entity
@Table(name = "event_comment")
public class EventComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_comment_no", nullable = false)
	private Integer eventCommentNo;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_no", nullable = false)
	private Event event;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no", nullable = false)
	private Integer memberNo;
	
	@Column(name = "event_comment_content")
	private String eventCommentContent;
	
	@Column(name = "event_comment_rate")
	private Integer eventCommentRate;
	
	@Column(name = "event_comment_time")
	private Timestamp eventCommentTime;
	
	@Column(name = "event_comment_status")
	private Byte eventCommentStatus;
	
	public EventComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventComment(Integer eventCommentNo, @NotNull Event event, @NotNull Integer memberNo,
			String eventCommentContent, Integer eventCommentRate, Timestamp eventCommentTime, Byte eventCommentStatus) {
		super();
		this.eventCommentNo = eventCommentNo;
		this.event = event;
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
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

	public Timestamp getEventCommentTime() {
		return eventCommentTime;
	}

	public void setEventCommentTime(Timestamp eventCommentTime) {
		this.eventCommentTime = eventCommentTime;
	}

	public Byte getEventCommentStatus() {
		return eventCommentStatus;
	}

	public void setEventCommentStatus(Byte eventCommentStatus) {
		this.eventCommentStatus = eventCommentStatus;
	}

	
	
	
}

