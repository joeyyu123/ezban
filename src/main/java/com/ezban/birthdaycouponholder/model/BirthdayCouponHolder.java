package com.ezban.birthdaycouponholder.model;

import java.time.LocalDate;
import javax.persistence.*;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.member.model.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "birthday_coupon_holder")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BirthdayCouponHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "birthday_coupon_holder_no", nullable = false)
    private Integer birthdayCouponHolderNo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @JsonBackReference
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

    public BirthdayCoupon getBirthdayCoupon() {
        return birthdayCoupon;
    }

    public void setBirthdayCoupon(BirthdayCoupon birthdayCoupon) {
        this.birthdayCoupon = birthdayCoupon;
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
}
