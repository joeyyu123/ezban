package com.ezban.birthdaycouponholder.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.member.model.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "birthday_coupon_holder")
public class BirthdayCouponHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "birthday_coupon_holder_no", nullable = false)
    private Integer birthdayCouponHolderNo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @JsonManagedReference
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birthday_coupon_no", nullable = false)
    private BirthdayCoupon birthdayCoupon;

    @Column(nullable = false)
    private Byte couponUsageStatus;

    @Column(name = "validity_date") 
    private LocalDate validityDate;

    // Getters and setters
    public Integer getBirthdayCouponHolderNo() {
        return birthdayCouponHolderNo;
    }

    public void setBirthdayCouponHolderNo(Integer birthdayCouponHolderNo) {
        this.birthdayCouponHolderNo = birthdayCouponHolderNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public Byte getCouponUsageStatus() {
        return couponUsageStatus;
    }

    public void setCouponUsageStatus(Byte couponUsageStatus) {
        this.couponUsageStatus = couponUsageStatus;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

	public BirthdayCoupon getBirthdayCoupon() {
		return birthdayCoupon;
	}

	public void setBirthdayCoupon(BirthdayCoupon birthdayCoupon) {
		this.birthdayCoupon = birthdayCoupon;
	}
    
    
}
