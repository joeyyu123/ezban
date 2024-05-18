package com.ezban.cart.model;

import java.util.List;

public class CheckoutRequest {
    private List<ProductInfo> products;

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public static class ProductInfo {
        private Integer productNo;
        private Integer quantity;

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
}
