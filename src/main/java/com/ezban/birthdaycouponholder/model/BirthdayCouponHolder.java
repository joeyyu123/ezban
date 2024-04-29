package com.ezban.birthdaycouponholder.model;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "birthday_coupon_holder")
public class BirthdayCouponHolder implements Serializable {
    @Id
    @Column(name = "birthday_coupon_holder_no", nullable = false)
    private Integer birthdayCouponHolderNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "birthday_coupon_no", nullable = false)
    private BirthdayCoupon birthdayCoupon;

    @NotNull
    @Column(name = "coupon_usage_status", nullable = false)
    private Byte couponUsageStatus;

    @NotNull
    @Column(name = "validity_date", nullable = false)
    private Timestamp validityDate;

    public Integer getBirthdayCouponHolderNo() {
        return birthdayCouponHolderNo;
    }

    public void setBirthdayCouponHolderNo(Integer birthdayCouponHolderNo) {
        this.birthdayCouponHolderNo = birthdayCouponHolderNo;
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

    public Timestamp getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Timestamp validityDate) {
        this.validityDate = validityDate;
    }

}