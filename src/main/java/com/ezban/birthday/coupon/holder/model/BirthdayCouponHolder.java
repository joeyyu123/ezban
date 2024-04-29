package com.ezban.birthday.coupon.holder.model;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ezban.birthday.coupon.model.BirthdayCoupon;



@Entity
@Table(name = "birthday_coupon_holder")
@IdClass(BirthdayCouponHolder.BirthdayCouponHolderId.class) 
public class BirthdayCouponHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no", referencedColumnName = "member_no", nullable = false)
	private Member member; 

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "birthday_coupon_no", referencedColumnName = "birthday_coupon_no", nullable = false)
	private BirthdayCoupon birthdayCoupon; 

	@Column(name = "coupon_usage_status", nullable = false)
	private byte couponUsageStatus; 

	@Column(name = "validity_date", nullable = false)
	private LocalDateTime validityDate; 

	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public BirthdayCoupon getBirthdayCoupon() {
		return birthdayCoupon;
	}

	public void setBirthdayCoupon(BirthdayCoupon birthdayCoupon) {
		this.birthdayCoupon = birthdayCoupon;
	}

	public byte getCouponUsageStatus() {
		return couponUsageStatus;
	}

	public void setCouponUsageStatus(byte couponUsageStatus) {
		this.couponUsageStatus = couponUsageStatus;
	}

	public LocalDateTime getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(LocalDateTime validityDate) {
		this.validityDate = validityDate;
	}

	
	static class BirthdayCouponHolderId implements Serializable {
		private Integer memberNo; 
		private Integer birthdayCouponNo; 

		public BirthdayCouponHolderId() {
		}

		public BirthdayCouponHolderId(Integer memberNo, Integer birthdayCouponNo) {
			this.memberNo = memberNo;
			this.birthdayCouponNo = birthdayCouponNo;
		}

		public Integer getMemberNo() {
			return memberNo;
		}

		public void setMemberNo(Integer memberNo) {
			this.memberNo = memberNo;
		}

		public Integer getBirthdayCouponNo() {
			return birthdayCouponNo;
		}

		public void setBirthdayCouponNo(Integer birthdayCouponNo) {
			this.birthdayCouponNo = birthdayCouponNo;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			BirthdayCouponHolderId that = (BirthdayCouponHolderId) o;
			return Objects.equals(memberNo, that.memberNo) && Objects.equals(birthdayCouponNo, that.birthdayCouponNo);
		}

		@Override
		public int hashCode() {
			return Objects.hash(memberNo, birthdayCouponNo);
		}
	}

}
