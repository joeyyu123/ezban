package com.ezban.productimg.model;

import com.ezban.product.model.Product;

import javax.persistence.*;


@Entity
@Table(name = "product_img", schema = "ezban")
public class ProductImg {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_img_no", nullable = false)
    private Integer productImgNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @Column(name = "product_img", columnDefinition = "longblob")
    private byte[] productImg;

    public ProductImg() {
    }

    public Integer getProductImgNo() {
        return productImgNo;
    }

    public void setProductImgNo(Integer productImgNo) {
        this.productImgNo = productImgNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public byte[] getProductImg() {
        return productImg;
    }

    public void setProductImg(byte[] productImg) {
        this.productImg = productImg;
    }

    // 回傳圖片url
    public String getImageUrl() {
        return "/product/getImage/" + this.productImgNo;
    }

}

