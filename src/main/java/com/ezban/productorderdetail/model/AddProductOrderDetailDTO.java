package com.ezban.productorderdetail.model;

import javax.validation.constraints.NotNull;

public class AddProductOrderDetailDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "* 商品編號: 請勿空白 !")
    private String productNo;

    @NotNull(message = "* 商品購買數量: 請勿空白 !")
    private Integer productQty;

    @NotNull(message = "* 商品金額: 請勿空白 !")
    private Integer productPrice;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
}