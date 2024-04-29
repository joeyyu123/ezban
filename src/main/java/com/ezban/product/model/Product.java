package com.ezban.product.model;

import com.ezban.host.model.Host;
import com.ezban.productcategory.model.ProductCategory;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Integer productNo;

    @ManyToOne
    @JoinColumn(name = "product_category_no", referencedColumnName = "product_category_no")
    private ProductCategory productCategory;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "host_no", referencedColumnName = "host_no")
    private Host host;

    @Column(name = "product_desc" ,columnDefinition = "text")
    private String productDesc;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "product_add_qty")
    private Integer productAddQty;

    @Column(name = "remaining_qty")
    private Integer remainingQty;

    @Column(name = "product_add_time")
    private Timestamp productAddTime;

    @Column(name = "product_remove_time")
    private Timestamp productRemoveTime;

    @Column(name = "product_status")
    private Integer productStatus;

    @Column(name = "product_total_rating")
    private Integer productTotalRating;

    @Column(name = "product_rating_count")
    private Integer productRatingCount;


    public Product() {
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
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

    public Timestamp getProductAddTime() {
        return productAddTime;
    }

    public void setProductAddTime(Timestamp productAddTime) {
        this.productAddTime = productAddTime;
    }

    public Timestamp getProductRemoveTime() {
        return productRemoveTime;
    }

    public void setProductRemoveTime(Timestamp productRemoveTime) {
        this.productRemoveTime = productRemoveTime;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getProductTotalRating() {
        return productTotalRating;
    }

    public void setProductTotalRating(Integer productTotalRating) {
        this.productTotalRating = productTotalRating;
    }

    public Integer getProductRatingCount() {
        return productRatingCount;
    }

    public void setProductRatingCount(Integer productRatingCount) {
        this.productRatingCount = productRatingCount;
    }
}

