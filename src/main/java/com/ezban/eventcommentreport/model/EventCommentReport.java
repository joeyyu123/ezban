package com.ezban.eventcommentreport.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ezban.admin.model.Admin;
import com.ezban.eventcomment.model.EventComment;
import com.ezban.member.model.Member;

@Entity
@Table(name = "event_comment_report")
public class EventCommentReport implements Serializable {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_report_no", nullable = false)
    private Integer eventCommentReportNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_comment_no", nullable = false)
    private EventComment eventComment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_no")
    private Admin admin;
    
    @Size(max = 100)
    @Lob
    @Column(name = "report_reason")
    private String reportReason;
    
    @NotNull
    @Column(name = "report_time", nullable = false)
    private Instant reportTime;

    @NotNull
    @Column(name = "report_status", nullable = false)
    private Byte reportStatus;

    // Getters and setters...

    public Integer getEventCommentReportNo() {
        return eventCommentReportNo;
    }

    public void setEventCommentReportNo(Integer eventCommentReportNo) {
        this.eventCommentReportNo = eventCommentReportNo;
    }

    public EventComment getEventComment() {
        return eventComment;
    }

    public void setEventComment(EventComment eventComment) {
        this.eventComment = eventComment;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public Instant getReportTime() {
        return reportTime;
    }

    public void setReportTime(Instant reportTime) {
        this.reportTime = reportTime;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    public EventCommentReportDTO toDTO() {
        return new EventCommentReportDTO(
            this.eventCommentReportNo,
            this.eventComment.getEventCommentNo(),
            this.eventComment.getEventCommentContent(),
            this.member.getMemberNo(),
            this.admin != null ? this.admin.getAdminNo() : null,
            this.reportReason,
            this.reportTime,
            this.reportStatus
        );
    }
}
