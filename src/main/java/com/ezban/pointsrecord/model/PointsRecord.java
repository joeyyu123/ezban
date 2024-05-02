package com.ezban.pointsrecord.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ezban.member.model.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_record")
public class PointsRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "points_record_no", nullable = false)
	private Integer pointsRecordNo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no", nullable = false)
	private Member member;

	@NotNull
	@Column(name = "points_changed", nullable = false)
	private Integer pointsChanged;

	@Column(name = "transaction_time")
	private Timestamp transactionTime;

	public PointsRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PointsRecord(Integer pointsRecordNo, @NotNull Member member, @NotNull Integer pointsChanged,
			Timestamp transactionTime) {
		super();
		this.pointsRecordNo = pointsRecordNo;
		this.member = member;
		this.pointsChanged = pointsChanged;
		this.transactionTime = transactionTime;
	}

	public Integer getPointsRecordNo() {
		return pointsRecordNo;
	}

	public void setPointsRecordNo(Integer pointsRecordNo) {
		this.pointsRecordNo = pointsRecordNo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getPointsChanged() {
		return pointsChanged;
	}

	public void setPointsChanged(Integer pointsChanged) {
		this.pointsChanged = pointsChanged;
	}

	public Timestamp getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "Points_record [pointsRecordNo=" + pointsRecordNo + ", member=" + member + ", pointsChanged="
				+ pointsChanged + ", transactionTime=" + transactionTime + "]";
	}

}
