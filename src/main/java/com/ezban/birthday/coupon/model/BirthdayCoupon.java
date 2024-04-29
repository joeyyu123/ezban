package com.ezban.birthday.coupon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;


	
@Entity
@Table(name = "birthday_coupon", schema = "ezban")
public class BirthdayCoupon {
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "birthday_coupon_no", nullable = false)
	private Integer birthdayCouponNo;
	
	@NonNull
	@Column(name = "birthday_coupon_discount", nullable = false)
	private Integer birthdayCouponDiscount;
	
	@NotNull
	@Column(name = "birthday_coupon_status", nullable = false)
	private Byte birthdayCouponStatus;
	
	@NotNull
	@Column(name = "valid_days" )
	private Integer validDays;

	public Integer getBirthdayCouponNo() {
		return birthdayCouponNo;
	}

	public void setBirthdayCouponNo(Integer birthdayCouponNo) {
		this.birthdayCouponNo = birthdayCouponNo;
	}

	public Integer getBirthdayCouponDiscount() {
		return birthdayCouponDiscount;
	}

	public void setBirthdayCouponDiscount(Integer birthdayCouponDiscount) {
		this.birthdayCouponDiscount = birthdayCouponDiscount;
	}

	public Byte getBirthdayCouponStatus() {
		return birthdayCouponStatus;
	}

	public void setBirthdayCouponStatus(Byte birthdayCouponStatus) {
		this.birthdayCouponStatus = birthdayCouponStatus;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

}
