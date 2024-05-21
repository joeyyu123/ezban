package com.ezban.cart.model;

import com.ezban.product.model.Product;

public class CartItems {

    private Product product;
    private Integer quantity;

    public CartItems() {
    }

    public CartItems(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
