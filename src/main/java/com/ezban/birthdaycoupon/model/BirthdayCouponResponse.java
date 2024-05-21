package com.ezban.birthdaycoupon.model;

public class BirthdayCouponResponse {
    private Integer birthdayCouponNo;
    private Integer birthdayCouponDiscount;
    private Integer memberNo;
    private Byte couponUsageStatus;
    private Byte birthdayCouponStatus;

    public BirthdayCouponResponse() {
    }

    public BirthdayCouponResponse(Integer birthdayCouponNo, Integer birthdayCouponDiscount, Integer memberNo, Byte couponUsageStatus, Byte birthdayCouponStatus) {
        this.birthdayCouponNo = birthdayCouponNo;
        this.birthdayCouponDiscount = birthdayCouponDiscount;
        this.memberNo = memberNo;
        this.couponUsageStatus = couponUsageStatus;
        this.birthdayCouponStatus = birthdayCouponStatus;
    }

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

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Byte getBirthdayCouponStatus() {
        return birthdayCouponStatus;
    }

    public void setBirthdayCouponStatus(Byte birthdayCouponStatus) {
        this.birthdayCouponStatus = birthdayCouponStatus;
    }

    public Byte getCouponUsageStatus() {
        return couponUsageStatus;
    }

    public void setCouponUsageStatus(Byte couponUsageStatus) {
        this.couponUsageStatus = couponUsageStatus;
    }


}
