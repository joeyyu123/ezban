package com.ezban.birthdaycoupon.model;

import com.ezban.birthdaycouponholder.model.BirthdayCouponHolder;
import com.ezban.productorder.model.ProductOrder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "birthday_coupon", schema = "ezban")
public class BirthdayCoupon {
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

    @OneToMany(mappedBy = "birthdayCoupon")
    private Set<BirthdayCouponHolder> birthdayCouponHolders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "birthdayCoupon")
    private Set<ProductOrder> productOrders = new LinkedHashSet<>();

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

    public Set<BirthdayCouponHolder> getBirthdayCouponHolders() {
        return birthdayCouponHolders;
    }

    public void setBirthdayCouponHolders(Set<BirthdayCouponHolder> birthdayCouponHolders) {
        this.birthdayCouponHolders = birthdayCouponHolders;
    }

    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Set<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

}