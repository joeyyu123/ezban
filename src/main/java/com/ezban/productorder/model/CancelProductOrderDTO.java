package com.ezban.productorder.model;

public class CancelProductOrderDTO {

    private Integer productOrderNo;

    private Byte productProcessStatus;

    public CancelProductOrderDTO() {
    }

    public Integer getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(Integer productOrderNo) {
        this.productOrderNo = productOrderNo;
    }

    public Byte getProductProcessStatus() {
        return productProcessStatus;
    }

    public void setProductProcessStatus(Byte productProcessStatus) {
        this.productProcessStatus = productProcessStatus;
    }
}
