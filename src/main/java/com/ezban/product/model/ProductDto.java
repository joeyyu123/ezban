package com.ezban.product.model;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProductDto {

    private Integer productNo;

    @NotNull(message = "商品名稱不得空白")
    private String productName;

    @NotNull(message = "請選擇商品類別")
    private Integer productCategoryNo;

    @NotNull
    private Integer hostNo;

    @NotNull(message = "商品描述不得空白")
    private String productDesc;

    @Min(value = 1, message = "價格必須大於0")
    @NotNull(message = "商品價格不得空白")
    private Integer productPrice;

    @Min(value = 1, message = "上架數量須大於0")
    @NotNull(message="上架數量不得空白")
    private Integer productAddQty;

    @NotNull(message = "庫存量不得空白")
    private Integer remainingQty;


    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    @Future(message = "日期需在今日之後")
    @NotNull(message = "上架日期不得空白")
    private String productAddTime;

    private String productRemoveTime;

    @NotNull
    private Byte productStatus;

    public Integer getProductCategoryNo() {
        return productCategoryNo;
    }

    public void setProductCategoryNo(Integer productCategoryNo) {
        this.productCategoryNo = productCategoryNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getHostNo() {
        return hostNo;
    }

    public void setHostNo(Integer hostNo) {
        this.hostNo = hostNo;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductAddQty() {
        return productAddQty;
    }

    public void setProductAddQty(Integer productAddQty) {
        this.productAddQty = productAddQty;
    }

    public Integer getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Integer remainingQty) {
        this.remainingQty = remainingQty;
    }

    public String getProductAddTime() {
        return productAddTime;
    }

    public Timestamp getProductAddTimeTimeStamp() {
        return convertStringToTimestamp(productAddTime);
    }

    public void setProductAddTime(String productAddTime) {
        this.productAddTime = productAddTime;
    }

    public String getProductRemoveTime() {
        return productRemoveTime;
    }

    public Timestamp getProductRemoveTimeTimeStamp() {
        return convertStringToTimestamp(productRemoveTime);
    }

    public void setProductRemoveTime(String productRemoveTime) {
        this.productRemoveTime = productRemoveTime;
    }

    public Byte getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Byte productStatus) {
        this.productStatus = productStatus;
    }


    public static Timestamp convertStringToTimestamp(String dateTimeStr) {
        if (dateTimeStr != null && !dateTimeStr.isEmpty()) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return Timestamp.valueOf(dateTime);
            } catch (DateTimeParseException e) {
                System.err.println("日期時間轉換失敗: " + e.getMessage());
            }
        }
        return null;
    }

}
