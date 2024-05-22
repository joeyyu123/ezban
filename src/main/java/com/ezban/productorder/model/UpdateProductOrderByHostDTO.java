package com.ezban.productorder.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public class UpdateProductOrderByHostDTO {
    private Integer productOrderNo;

    @NotEmpty(message = "* 收件人: 請勿空白 !")
    @Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$", message = "* 姓名: 只能是中、英文字母 , 且長度必需在2到10之間 !")
    private String recipient;

    @NotEmpty(message = "* 收件人電話: 請勿空白 !")
    @Pattern(regexp = "^0[0-9]{8,15}$", message = "* 電話號碼格式不正確 !")
    private String recipientPhone;

    @NotEmpty(message = "* 收件人地址: 請勿空白 !")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9 ]{2,200}$", message = "* 地址格式不正確 !")
    private String recipientAddress;

    private Byte productProcessStatus;

    public Integer getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(Integer productOrderNo) {
        this.productOrderNo = productOrderNo;
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

    public Byte getProductProcessStatus() {
        return productProcessStatus;
    }

    public void setProductProcessStatus(Byte productProcessStatus) {
        this.productProcessStatus = productProcessStatus;
    }

}