package com.ezban.cart.model;

public class AddToCartRequest {
    private Integer productNo;
    private Integer quantity;

    public AddToCartRequest() {
    }

    public AddToCartRequest(Integer productNo, Integer quantity) {
        this.productNo = productNo;
        this.quantity = quantity;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
