package com.ezban.productorder.model;

import com.ezban.productorderdetail.model.AddProductOrderDetailDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class AddProductOrderDTO {
    private Integer productOrderNo;

    @NotNull(message = "* 會員編號: 請勿空白 !")
    private String memberNo;

    @NotNull(message = "* 商品金額: 請勿空白 !")
    @Min(value = 1, message = "* 商品金額: 不能小於{value} !")
    private String productPrice;

    private String memberPoints;

    private String birthdayCouponNo;

    private String productCouponDiscount;

    @NotNull(message = "* 結帳金額: 請勿空白 !")
    private String productCheckoutAmount;

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

    private String memberRemainingPoints;

    private List<AddProductOrderDetailDTO> productOrderDetail;

    public Integer getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(Integer productOrderNo) {
        this.productOrderNo = productOrderNo;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(String memberPoints) {
        this.memberPoints = memberPoints;
    }

    public String getBirthdayCouponNo() {
        return birthdayCouponNo;
    }

    public void setBirthdayCouponNo(String birthdayCouponNo) {
        this.birthdayCouponNo = birthdayCouponNo;
    }

    public String getProductCouponDiscount() {
        return productCouponDiscount;
    }

    public void setProductCouponDiscount(String productCouponDiscount) {
        this.productCouponDiscount = productCouponDiscount;
    }

    public String getProductCheckoutAmount() {
        return productCheckoutAmount;
    }

    public void setProductCheckoutAmount(String productCheckoutAmount) {
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

    public String getMemberRemainingPoints() {
        return memberRemainingPoints;
    }

    public void setMemberRemainingPoints(String memberRemainingPoints) {
        this.memberRemainingPoints = memberRemainingPoints;
    }

    public List<AddProductOrderDetailDTO> getProductOrderDetail() {
        return productOrderDetail;
    }

    public void setProductOrderDetail(List<AddProductOrderDetailDTO> productOrderDetail) {
        this.productOrderDetail = productOrderDetail;
    }
}
