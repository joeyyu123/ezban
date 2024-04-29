package com.ezban.event.comment.report.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event_comment_report")
public class EventCommentReport {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_report_no")
    private Integer eventCommentReportNo; // 活動評論檢舉編號

    @NotNull
    @Column(name = "event_comment_no")
    private Integer eventCommentNo;       // 活動評論編號
    
    @NotNull
    @Column(name = "member_no")
    private Integer memberNo;             // 會員編號 (檢舉者)
    
    @NotNull
    @Column(name = "admin_no")
    private Integer adminNo;              // 後台管理員編號 (審核者)
    
    @NotNull
    @Column(name = "report_reason", columnDefinition = "TEXT")
    private String reportReason;          // 檢舉原因
    
    @NotNull
    @Column(name = "report_date")
    private LocalDateTime reportDate;     // 檢舉日期
    
    @NotNull
    @Column(name = "report_status")
    private Byte reportStatus;            // 活動評論檢舉狀態

    // Constructor
    public EventCommentReport() {
    }

	public Integer getEventCommentReportNo() {
		return eventCommentReportNo;
	}

	public void setEventCommentReportNo(Integer eventCommentReportNo) {
		this.eventCommentReportNo = eventCommentReportNo;
	}

	public Integer getEventCommentNo() {
		return eventCommentNo;
	}

	public void setEventCommentNo(Integer eventCommentNo) {
		this.eventCommentNo = eventCommentNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}

	public Byte getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Byte reportStatus) {
		this.reportStatus = reportStatus;
	}

}
