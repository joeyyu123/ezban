package com.ezban.productorderdetail.model;

import com.ezban.product.model.Product;
import com.ezban.productorder.model.ProductOrder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_order_detail", schema = "ezban")
public class ProductOrderDetail implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_detail_no", nullable = false)
    private Integer productOrderDetailNo;

    @NotNull(message = "* 商品編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @NotNull(message = "* 商品訂單編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "product_order_no", nullable = false)
    private ProductOrder productOrder;

    @NotNull(message = "* 商品購買數量: 請勿空白 !")
    @Column(name = "product_qty", nullable = false)
    private Integer productQty;

    @NotNull(message = "* 商品金額: 請勿空白 !")
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @NotNull(message = "* 評論狀態: 請勿空白 !")
    @Column(name = "comments_status", nullable = false)
    @Min(value = 0, message = "* 評論狀態不正確 !")
    @Max(value = 1, message = "* 評論狀態不正確 !")
    private Byte commentsStatus;

    public ProductOrderDetail() {
    }

    public Integer getProductOrderDetailNo() {
        return productOrderDetailNo;
    }

    public void setProductOrderDetailNo(Integer productOrderDetailNo) {
        this.productOrderDetailNo = productOrderDetailNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductOrder getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
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

    public Byte getCommentsStatus() {
        return commentsStatus;
    }

    public void setCommentsStatus(Byte commentsStatus) {
        this.commentsStatus = commentsStatus;
    }
}