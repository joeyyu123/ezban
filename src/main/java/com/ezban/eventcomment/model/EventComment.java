package com.ezban.eventcomment.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ezban.event.model.Event;
import com.ezban.member.model.Member;

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
	private Member member;
	
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

	public EventComment(Integer eventCommentNo, @NotNull Event event, @NotNull Member member,
			String eventCommentContent, Integer eventCommentRate, Timestamp eventCommentTime, Byte eventCommentStatus) {
		super();
		this.eventCommentNo = eventCommentNo;
		this.event = event;
		this.member = member;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	@Override
	public String toString() {
		return "EventComment [eventCommentNo=" + eventCommentNo + ", event=" + event + ", member=" + member
				+ ", eventCommentContent=" + eventCommentContent + ", eventCommentRate=" + eventCommentRate
				+ ", eventCommentTime=" + eventCommentTime + ", eventCommentStatus=" + eventCommentStatus + "]";
	}
	
	
}

