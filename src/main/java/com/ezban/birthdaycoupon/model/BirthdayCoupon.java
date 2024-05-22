package com.ezban.birthdaycoupon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ezban.member.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "birthday_coupon")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BirthdayCoupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "birthday_coupon_no", nullable = false)
    private Integer birthdayCouponNo;

    @NotNull
    @Column(name = "birthday_coupon_discount", nullable = false)
    private Integer birthdayCouponDiscount;

    @NotNull
    @Column(name = "birthday_coupon_status", nullable = false)
    private Byte birthdayCouponStatus;

    @NotNull
    @Column(name = "valid_days", nullable = false)
    private Integer validDays;

    // Optional: Relationship with Member (if each coupon is directly linked to a Member)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_no")
//    private Member member;

    // Getters and Setters
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

//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }
}
