package com.ezban.productorder.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.saveproduct.model.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "product_order", schema = "ezban")
public class ProductOrder implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_no", nullable = false)
    private Integer productOrderNo;

    @NotNull(message = "會員編號: 請勿空白!")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @NotNull(message = "商品金額: 請勿空白!")
    @Min(value = 1, message = "商品金額: 不能小於{value}")
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "product_coupon_discount", columnDefinition = "int default 0")
    private Integer productCouponDiscount;

    @NotNull(message = "結帳金額: 請勿空白!")
    @Column(name = "product_checkout_amount", nullable = false)
    private Integer productCheckoutAmount;

    @Column(name = "member_points", columnDefinition = "int default 0")
    private Integer memberPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birthday_coupon_no")
    private BirthdayCoupon birthdayCoupon;

    @NotNull(message = "收件人: 請勿空白!")
    @Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$", message = "姓名: 只能是中、英文字母 , 且長度必需在2到10之間")
    @Column(name = "recipient", nullable = false, length = 50)
    private String recipient;

    @NotNull(message = "收件人電話: 請勿空白!")
    @Pattern(regexp = "^0[0-9]{8,9}$", message = "電話號碼格式不正確")
    @Column(name = "recipient_phone", nullable = false, length = 15)
    private String recipientPhone;

    @NotNull(message = "收件人地址: 請勿空白!")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9 ]{2,200}$", message = "地址格式不正確")
    @Column(name = "recipient_address", nullable = false, length = 200)
    private String recipientAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "product_orderdate")
    private Timestamp productOrderdate = Timestamp.valueOf(LocalDateTime.now());
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "product_paydate")
    private Timestamp productPaydate = Timestamp.valueOf(LocalDateTime.now());

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "order_closedate", nullable = true)
    private Timestamp orderClosedate;

    @NotNull(message = "商品訂單付款狀態: 請勿空白!")
    @Column(name = "product_payment_status", nullable = false)
    @Pattern(regexp = "^[0-2]$", message = "商品訂單付款狀態不正確")
    private Byte productPaymentStatus;

    @NotNull(message = "商品訂單處理狀態: 請勿空白!")
    @Column(name = "product_process_status", nullable = false)
    @Pattern(regexp = "^[0-4]$", message = "商品訂單處理狀態不正確")
    private Byte productProcessStatus;

    @NotNull(message = "商品訂單撥款金額: 請勿空白!")
    @Column(name = "product_order_allocation_amount", nullable = false)
    private Integer productOrderAllocationAmount;

    @NotNull(message = "商品訂單撥款狀態: 請勿空白!")
    @Column(name = "product_order_allocation_status", nullable = false)
    @Pattern(regexp = "^[0-1]$", message = "商品訂單撥款狀態不正確")
    private Byte productOrderAllocationStatus;

    @OneToMany(mappedBy = "productOrder")
    private List<ProductOrderDetail> productOrderDetails;

    public ProductOrder() {

    }

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

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public BirthdayCoupon getBirthdayCoupon() {
        return birthdayCoupon;
    }

    public void setBirthdayCouponVO(BirthdayCoupon birthdayCoupon) {
        this.birthdayCoupon = birthdayCoupon;
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

    public Timestamp getProductOrderdate() {
        return productOrderdate;
    }

    public void setProductOrderdate(Timestamp productOrderdate) {
        this.productOrderdate = productOrderdate;
    }

    public Timestamp getProductPaydate() {
        return productPaydate;
    }

    public void setProductPaydate(Timestamp productPaydate) {
        this.productPaydate = productPaydate;
    }

    public Timestamp getOrderClosedate() {
        return orderClosedate;
    }

    public void setOrderClosedate(Timestamp orderClosedate) {
        this.orderClosedate = orderClosedate;
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

    public List<ProductOrderDetail> getProductOrderDetails() {
        return productOrderDetails;
    }

    public void setProductOrderDetails(List<ProductOrderDetail> productOrderDetails) {
        this.productOrderDetails = productOrderDetails;
    }
}