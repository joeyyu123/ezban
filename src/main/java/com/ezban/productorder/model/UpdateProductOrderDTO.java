package com.ezban.productorder.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
public class UpdateProductOrderDTO {
    private Integer productOrderNo;

    private Timestamp orderClosedate;

    private Byte productPaymentStatus;

    private Byte productProcessStatus;

    private Byte productOrderAllocationStatus;

    public Integer getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(Integer productOrderNo) {
        this.productOrderNo = productOrderNo;
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

    public Byte getProductOrderAllocationStatus() {
        return productOrderAllocationStatus;
    }

}
