package com.ezban.cart.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutRequest {
    private Map<Integer, Integer> products = new HashMap<>();

    public String getProducts() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 將 map 轉換為 JSON 字符串
            return mapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // 發生錯誤時返回空的 JSON 對象
        }
    }

//    public void setProducts(List<ProductInfo> productList) {
//        this.products.clear(); // 清除現有資料
//        if (productList != null) {
//            for (ProductInfo productInfo : productList) {
//                this.products.put(productInfo.getProductNo(), productInfo.getQuantity());
//            }
//        }
//    }

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
