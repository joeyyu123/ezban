package com.ezban.eventcommentreport.model;

import com.ezban.admin.model.Admin;
import com.ezban.eventcomment.model.EventComment;
import com.ezban.member.model.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "event_comment_report", schema = "ezban")
public class EventCommentReport {
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

    @Lob
    @Column(name = "report_reason")
    private String reportReason;

    @NotNull
    @Column(name = "report_time", nullable = false)
    private Timestamp reportTime;

    @NotNull
    @Column(name = "report_status", nullable = false)
    private Byte reportStatus;

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

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

}