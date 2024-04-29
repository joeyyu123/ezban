package com.ezban.pointsrecord.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PointsRecord {

	@Entity
	@Table(name = "points_record")
	public class Points_record {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "points_record_no", nullable = false)
		private Integer pointsRecordNo;

		@NotNull
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "member_no", nullable = false)
		private Integer memberNo;

		@NotNull
		@Column(name = "points_changed", nullable = false)
		private Integer pointsChanged;

		@Temporal(TemporalType.DATE)
		@Column(name = "transaction_time")
		private LocalDateTime transactionTime;

		public Points_record() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Points_record(Integer pointsRecordNo, Integer memberNo, Integer pointsChanged,
				LocalDateTime transactionTime) {
			super();
			this.pointsRecordNo = pointsRecordNo;
			this.memberNo = memberNo;
			this.pointsChanged = pointsChanged;
			this.transactionTime = transactionTime;
		}

		public Integer getPointsRecordNo() {
			return pointsRecordNo;
		}

		public void setPointsRecordNo(Integer pointsRecordNo) {
			this.pointsRecordNo = pointsRecordNo;
		}

		public Integer getMemberNo() {
			return memberNo;
		}

		public void setMemberNo(Integer memberNo) {
			this.memberNo = memberNo;
		}

		public Integer getPointsChanged() {
			return pointsChanged;
		}

		public void setPointsChanged(Integer pointsChanged) {
			this.pointsChanged = pointsChanged;
		}

		public LocalDateTime getTransactionTime() {
			return transactionTime;
		}

		public void setTransactionTime(LocalDateTime transactionTime) {
			this.transactionTime = transactionTime;
		}

		@Override
		public String toString() {
			return "Points_record [pointsRecordNo=" + pointsRecordNo + ", memberNo=" + memberNo + ", pointsChanged="
					+ pointsChanged + ", transactionTime=" + transactionTime + "]";
		}
	}

}
