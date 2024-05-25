package com.ezban.eventcomment.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ezban.event.model.Event;
import com.ezban.eventcommentreport.model.EventCommentReport;
import com.ezban.member.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "event_comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventComment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_no", nullable = false)
    private Integer eventCommentNo;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_no", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Event event;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Member member;
    
    @Column(name = "event_comment_content")
    private String eventCommentContent;
    
    @Min(1)
    @Column(name = "event_comment_rate")
    private Integer eventCommentRate;
    
    @Column(name = "event_comment_time")
    private LocalDateTime eventCommentTime;
    
    @Column(name = "event_comment_status")
    private Byte eventCommentStatus;

    @OneToMany(mappedBy = "eventComment")
    private Set<EventCommentReport> eventCommentReports = new LinkedHashSet<>();

    public Set<EventCommentReport> getEventCommentReports() {
        return eventCommentReports;
    }

    public void setEventCommentReports(Set<EventCommentReport> eventCommentReports) {
        this.eventCommentReports = eventCommentReports;
    }

    public EventComment() {
        super();
        // Default constructor
    }

    public EventComment(Integer eventCommentNo, @NotNull Event event, @NotNull Member member,
            String eventCommentContent, Integer eventCommentRate, LocalDateTime eventCommentTime, Byte eventCommentStatus) {
        super();
        this.eventCommentNo = eventCommentNo;
        this.event = event;
        this.member = member;
        this.eventCommentContent = eventCommentContent;
        this.eventCommentRate = eventCommentRate;
        this.eventCommentTime = eventCommentTime;
        this.eventCommentStatus = eventCommentStatus;
    }
    
    public EventComment(EventCommentDTO commentDTO, Member member, Event event) {
        this.event = event;
        this.member = member;
        this.eventCommentContent = commentDTO.getEventCommentContent();
        this.eventCommentRate = commentDTO.getEventCommentRate();
        this.eventCommentStatus = commentDTO.getEventCommentStatus();
        this.eventCommentTime = LocalDateTime.now(); 
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
        return "EventComment [eventCommentNo=" + eventCommentNo + ", event=" + event + ", member=" + member
                + ", eventCommentContent=" + eventCommentContent + ", eventCommentRate=" + eventCommentRate
                + ", eventCommentTime=" + eventCommentTime + ", eventCommentStatus=" + eventCommentStatus + "]";
    }
}
