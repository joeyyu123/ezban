package com.ezban.productorder.model;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.member.model.Member;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddProductOrderDTO {
    private Integer productOrderNo;

    @NotNull(message = "* 會員編號: 請勿空白 !")
    private Member member;

    @NotNull(message = "* 商品金額: 請勿空白 !")
    @Min(value = 1, message = "* 商品金額: 不能小於{value} !")
    private Integer productPrice;

    private Integer memberPoints;

    private Integer birthdayCouponNo;

    private Integer productCouponDiscount;

    @NotNull(message = "* 結帳金額: 請勿空白 !")
    private Integer productCheckoutAmount;

    @NotNull(message = "* 收件人: 請勿空白 !")
    @Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$", message = "* 姓名: 只能是中、英文字母 , 且長度必需在2到10之間 !")
    private String recipient;

    @NotNull(message = "* 收件人電話: 請勿空白 !")
    @Pattern(regexp = "^0[0-9]{8,15}$", message = "* 電話號碼格式不正確 !")
    private String recipientPhone;

    @NotNull(message = "* 收件人地址: 請勿空白 !")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9 ]{2,200}$", message = "* 地址格式不正確 !")
    private String recipientAddress;

    private Byte productPaymentStatus;

    private Byte productProcessStatus;

    private Integer productOrderAllocationAmount;

    private Byte productOrderAllocationStatus;

    public Integer getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(Integer productOrderNo) {
        this.productOrderNo = productOrderNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public Integer getBirthdayCouponNo() {
        return birthdayCouponNo;
    }

    public void setBirthdayCouponNo(Integer birthdayCouponNo) {
        this.birthdayCouponNo = birthdayCouponNo;
    }

    public Integer getProductCouponDiscount() {
        return productCouponDiscount;
    }

    public void setProductCouponDiscount(Integer productCouponDiscount) {
        this.productCouponDiscount = productCouponDiscount;
    }

    public Integer getProductCheckoutAmount() {
        return productCheckoutAmount;
    }

    public void setProductCheckoutAmount(Integer productCheckoutAmount) {
        this.productCheckoutAmount = productCheckoutAmount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Byte getProductPaymentStatus() {
        return productPaymentStatus;
    }

    public void setProductPaymentStatus(Byte productPaymentStatus) {
        this.productPaymentStatus = productPaymentStatus;
    }

    public Byte getProductProcessStatus() {
        return productProcessStatus;
    }

    public void setProductProcessStatus(Byte productProcessStatus) {
        this.productProcessStatus = productProcessStatus;
    }

    public Integer getProductOrderAllocationAmount() {
        return productOrderAllocationAmount;
    }

    public void setProductOrderAllocationAmount(Integer productOrderAllocationAmount) {
        this.productOrderAllocationAmount = productOrderAllocationAmount;
    }

    public Byte getProductOrderAllocationStatus() {
        return productOrderAllocationStatus;
    }

    public void setProductOrderAllocationStatus(Byte productOrderAllocationStatus) {
        this.productOrderAllocationStatus = productOrderAllocationStatus;
    }

}
